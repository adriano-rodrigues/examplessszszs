package com.monitoratec.tokenservice.vtswalletservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.client.enroll.DeviceEnrollmentClient;
import com.monitoratec.tokenservice.vtswalletservice.client.provisiontoken.ProvisionedTokensForAPanClient;
import com.monitoratec.tokenservice.vtswalletservice.common.ApplicationProperties;
import com.monitoratec.tokenservice.vtswalletservice.common.EncryptionGenerator;
import com.monitoratec.tokenservice.vtswalletservice.common.XPayTokenGenerator;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.device.CertUsage;
import com.monitoratec.tokenservice.vtswalletservice.domain.model.DeviceEnrollmentModel;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.DasDeviceEnrollmentPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.delete.DeleteDeviceTokenBindingPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.DeviceCerts;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.EnrollDevicePayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind.EnrollDeviceTokenBindPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.device.enrollment.das.DasDeviceEnrollmentOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.device.enrollment.device.EnrollDeviceOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.device.enrollment.tokenbind.EnrollDeviceTokenBindOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.repository.EnrollDeviceRepository;
import com.monitoratec.tokenservice.vtswalletservice.service.EnrollDeviceService;
import com.nimbusds.jose.JOSEException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.monitoratec.tokenservice.vtswalletservice.util.JSONUtil.toJson;
import static com.monitoratec.tokenservice.vtswalletservice.util.WalletServiceUtil.generateClientDeviceID;
import static com.monitoratec.tokenservice.vtswalletservice.util.WalletServiceUtil.generateXRequestID;

@Service
public class EnrollDeviceServiceImpl implements EnrollDeviceService {

    private static Logger logger = LoggerFactory.getLogger(EnrollDeviceServiceImpl.class);
    @Autowired
    private DeviceEnrollmentClient deviceEnrollmentClient;
    @Autowired
    private ProvisionedTokensForAPanClient provisionedTokensForAPanClient;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private XPayTokenGenerator xPayTokenGenerator;
    @Autowired
    private EncryptionGenerator encryptionGenerator;
    @Autowired
    private EnrollDeviceRepository enrollDeviceRepository;

    private ObjectMapper MAPPER = new ObjectMapper();


    @Override
    public ResponseEntity<String> putDeviceEnrollmentToVtsClient(EnrollDevicePayload request) throws IOException, XPayTokenException {
        EnrollDeviceOutputPayload enrollDeviceOutputPayload;
        enrollDeviceOutputPayload = MAPPER.convertValue(request.getEnrollDevice(), EnrollDeviceOutputPayload.class);
        List<DeviceCerts> deviceCerts = new ArrayList<>();
        deviceCerts.add(DeviceCerts.builder()
                        .certFormat("X509")
                        .certUsage(CertUsage.CONFIDENTIALITY)
                        .certValue(applicationProperties.getFirstTechDeviceCertsSignedConfidentialityPem())
                .build());
        deviceCerts.add(DeviceCerts.builder()
                        .certFormat("X509")
                        .certUsage(CertUsage.INTEGRITY)
                        .certValue(applicationProperties.getFirstTechDeviceCertsSignedConfidentialityCrt())
                .build());
        enrollDeviceOutputPayload.getChannelSecurityContext().setDeviceCerts(deviceCerts);
        String clientDeviceId = generateClientDeviceID(24, enrollDeviceOutputPayload.getDeviceInfo().getHostDeviceID());
        String resourcePath = prepareDeviceEnrollmentResourcePath(applicationProperties.getDeviceEnrollmentPath(), request.getVClientId(), clientDeviceId);
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String outputPayload = MAPPER.writeValueAsString(enrollDeviceOutputPayload);
        String xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryStringApiKey,outputPayload,request.getSharedSecret());
        String xRequestId = generateXRequestID();

        ResponseEntity<String> response = null;
        try {
            response = deviceEnrollmentClient.putDeviceEnrollment(
                    request.getVClientId(),
                    clientDeviceId,
                    request.getApiKey(),
                    xPayToken,
                    xRequestId,
                    outputPayload);
        }catch(FeignException e){
            enrollDeviceRepository.save(DeviceEnrollmentModel.builder()
                    ._id(xRequestId)
                    .createdAt(LocalDateTime.now())
                    .modelOfErrorVtsCall(MAPPER.writeValueAsString(e))
                    .enrollDeviceRequestBody(toJson(enrollDeviceOutputPayload))
                    .build());
            throw e;
        }catch (Exception e){
            enrollDeviceRepository.save(DeviceEnrollmentModel.builder()
                    ._id(xRequestId)
                    .enrollDeviceRequestBody(toJson(enrollDeviceOutputPayload))
                    .modelOfGenericInternalError(e.getMessage())
                    .build());
            throw e;
        }
        enrollDeviceRepository.save(DeviceEnrollmentModel.builder()
                ._id(xRequestId)
                .enrollDeviceRequestBody(toJson(enrollDeviceOutputPayload))
                .modelOfSuccessVtsCall(toJson(response))
                .build());
        logger.info("_______ END putDeviceEnrollment __________ ");

        return response;
    }

    @Override
    public ResponseEntity<String> postTokenBindToEnrolledDevice(EnrollDeviceTokenBindPayload request) throws JsonProcessingException, XPayTokenException, NoSuchAlgorithmException, JOSEException {
        EnrollDeviceTokenBindOutputPayload enrollDeviceTokenBindOutputPayload;
        enrollDeviceTokenBindOutputPayload = MAPPER.convertValue(request,EnrollDeviceTokenBindOutputPayload.class);
        enrollDeviceTokenBindOutputPayload.setEncDeviceRiskDataInfo(encryptionGenerator.encryptData(request.getDeviceRiskDataInfo()));
        String resourcePath = prepareDeviceEnrollmentProvisionedIdResourcePath(applicationProperties.getProvisionedTokensForAEnrolledDevice(), request.getvProvisionedTokenID());
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String outputPayLoad =MAPPER.writeValueAsString(enrollDeviceTokenBindOutputPayload);
        String xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryStringApiKey,outputPayLoad,request.getSharedSecret());
        String xRequestId = generateXRequestID();

        ResponseEntity<String> response = null;

        response = provisionedTokensForAPanClient.postProvisionedTokensForAEnrolledDevice(
                request.getvProvisionedTokenID(),
                request.getApiKey(),
                xPayToken,
                xRequestId,
                outputPayLoad
        );
        logger.info("_______ END postTokenBindToEnrolledDevice __________ ");

        return response;
    }

    @Override
    public ResponseEntity<String> deleteDeviceTokenBinding(DeleteDeviceTokenBindingPayload request) throws XPayTokenException{
        String resourcePath = prepareDeleteDeviceTokenBindingPath(applicationProperties.getProvisionedTokensForDeleteDeviceTokenBinding(), request.getvProvisionedTokenID(), request.getClientDeviceID());
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String payload = "";
        String xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryStringApiKey,payload, request.getSharedSecret());
        String xRequestId = generateXRequestID();

        ResponseEntity<String> response = null;

        response = provisionedTokensForAPanClient.deleteDeviceTokenBinding(
            request.getvProvisionedTokenID(),
            request.getClientDeviceID(),
            request.getApiKey(),
            xPayToken,
            xRequestId,
            payload
        );
        logger.info("_______ END deleteDeviceTokenBinding __________ ");

        return response;
    }

    @Override
    public ResponseEntity<String> postDasDeviceEnrollment(DasDeviceEnrollmentPayload request) throws GenericSecurityException, JsonProcessingException{
        DasDeviceEnrollmentOutputPayload dasDeviceEnrollmentOutputPayload;
        dasDeviceEnrollmentOutputPayload = MAPPER.convertValue(request.getDasDeviceEnrollment(), DasDeviceEnrollmentOutputPayload.class);
        String outputPayLoad = MAPPER.writeValueAsString(dasDeviceEnrollmentOutputPayload);
        String xServiceContext = ""; // header injected by RLS

        ResponseEntity<String> response = null;

        response = deviceEnrollmentClient.postDasDeviceEnrollment(
            request.getVClientID(),
            request.getClientDeviceID(),
            request.getApiKey(),
            xServiceContext,
            outputPayLoad
        );
        logger.info("_______ END postDasDeviceEnrollment __________ ");

        return response;
    }

    private String prepareDeviceEnrollmentResourcePath(String resourcePath, String vClientId, String deviceId){
        resourcePath = resourcePath.replace("{vClientId}",vClientId);
        resourcePath = resourcePath.replace("{clientDeviceId}",deviceId);
        return resourcePath;
    }

    private String prepareDeviceEnrollmentProvisionedIdResourcePath(String resourcePath, String vProvisionedTokenID){
        resourcePath = resourcePath.replace("{vProvisionedTokenID}",vProvisionedTokenID);
        return resourcePath;
    }

    private String prepareDeleteDeviceTokenBindingPath(String resourcePath, String vProvisionedTokenID, String clientDeviceID){
        resourcePath = resourcePath.replace("{vProvisionedTokenID}",vProvisionedTokenID);
        resourcePath = resourcePath.replace("{clientDeviceID}", clientDeviceID);
        return resourcePath;
    }

}
