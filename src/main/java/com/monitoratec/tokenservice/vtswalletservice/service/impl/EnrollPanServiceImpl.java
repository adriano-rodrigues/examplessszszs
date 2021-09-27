package com.monitoratec.tokenservice.vtswalletservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.client.enroll.EnrollPanClient;
import com.monitoratec.tokenservice.vtswalletservice.common.ApplicationProperties;
import com.monitoratec.tokenservice.vtswalletservice.common.EncryptionGenerator;
import com.monitoratec.tokenservice.vtswalletservice.common.XPayTokenGenerator;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.device.CertUsage;
import com.monitoratec.tokenservice.vtswalletservice.domain.model.EnrollPanModel;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.DeviceCerts;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment.EnrollPanPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.pan.enrollment.EnrollPanOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.repository.EnrollPanRepository;
import com.monitoratec.tokenservice.vtswalletservice.service.EnrollPanService;
import com.nimbusds.jose.JOSEException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import static com.monitoratec.tokenservice.vtswalletservice.util.JSONUtil.toJson;
import static com.monitoratec.tokenservice.vtswalletservice.util.WalletServiceUtil.generateXRequestID;

@Service
public class EnrollPanServiceImpl implements EnrollPanService {

    private static Logger logger = LoggerFactory.getLogger(EnrollPanServiceImpl.class);
    @Autowired
    private EnrollPanClient enrollPanClient;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private XPayTokenGenerator xPayTokenGenerator;
    @Autowired
    private EncryptionGenerator encryptionGenerator;
    @Autowired
    private EnrollPanRepository enrollPanRepository;

    private ObjectMapper MAPPER = new ObjectMapper();


    @Override
    public ResponseEntity<String> postEnrollPanToVtsClient(EnrollPanPayload request) throws XPayTokenException, IOException, JOSEException, NoSuchAlgorithmException, ParseException {
        EnrollPanOutputPayload enrollPanOutputPayload = MAPPER.convertValue(request.getEnrollPan(),EnrollPanOutputPayload.class);
        enrollPanOutputPayload.getChannelSecurityContext().getDeviceCerts().add(DeviceCerts.builder()
                .certFormat("X509")
                .certUsage(CertUsage.DEVICE_ROOT)
                .certValue(applicationProperties.getFirstTechDeviceCertsSignedVdpcaPem())
                .build());
        String paymentInstrumentJweEncrypted = encryptionGenerator.encryptData( request.getEnrollPan().getPaymentInstrument());
        enrollPanOutputPayload.setEncPaymentInstrument(paymentInstrumentJweEncrypted);
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String payLoad = MAPPER.writeValueAsString(enrollPanOutputPayload);
        String xPayToken = xPayTokenGenerator.generateXpayToken(applicationProperties.getEnrollPanPath(), queryStringApiKey,payLoad,request.getSharedSecret());
        String xRequestId = generateXRequestID();
        ResponseEntity<String> response = null;
        try {
            response = enrollPanClient.postEnrollPan(
                request.getApiKey(),
                xPayToken,
                xRequestId,
                payLoad);
        }catch(FeignException e){
            enrollPanRepository.save(EnrollPanModel.builder()
                ._id(xRequestId)
                .modelOfErrorVtsCall(MAPPER.writeValueAsString(e))
                .enrollPanRequestBody(toJson(enrollPanOutputPayload))
                .build());
            throw e;
        }catch (Exception e){
            enrollPanRepository.save(EnrollPanModel.builder()
                ._id(xRequestId)
                .enrollPanRequestBody(toJson(enrollPanOutputPayload))
                .modelOfGenericInternalError(e.getMessage())
                .build());
            throw e;
        }
        enrollPanRepository.save(EnrollPanModel.builder()
            ._id(xRequestId)
            .enrollPanRequestBody(toJson(enrollPanOutputPayload))
            .modelOfSuccessVtsCall(toJson(response))
            .build());
        logger.info("_______ END postPanEnrollment __________ ");
        return response;
    }
}
