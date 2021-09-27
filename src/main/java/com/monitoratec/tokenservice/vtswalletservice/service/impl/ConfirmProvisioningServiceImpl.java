package com.monitoratec.tokenservice.vtswalletservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.client.provisioning.ConfirmProvisioningClient;
import com.monitoratec.tokenservice.vtswalletservice.common.ApplicationProperties;
import com.monitoratec.tokenservice.vtswalletservice.common.XPayTokenGenerator;
import com.monitoratec.tokenservice.vtswalletservice.domain.model.ConfirmProvisioningModel;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning.ConfirmProvisioningPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.confirm.provisioning.ConfirmProvisioningOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.repository.ConfirmProvisioningRepository;
import com.monitoratec.tokenservice.vtswalletservice.service.ConfirmProvisioningService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.monitoratec.tokenservice.vtswalletservice.util.JSONUtil.toJson;
import static com.monitoratec.tokenservice.vtswalletservice.util.WalletServiceUtil.generateXRequestID;

@Service
public class ConfirmProvisioningServiceImpl implements ConfirmProvisioningService {

    private static Logger logger = LoggerFactory.getLogger(EnrollDeviceServiceImpl.class);
    @Autowired
    private ConfirmProvisioningClient confirmProvisioningClient;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private XPayTokenGenerator xPayTokenGenerator;
    @Autowired
    private ConfirmProvisioningRepository confirmProvisioningRepository;
    private ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public ResponseEntity putConfirmProvisioningToVtsClient(ConfirmProvisioningPayload request) throws XPayTokenException, JsonProcessingException {
        ConfirmProvisioningOutputPayload confirmProvisioningOutputPayload;
        confirmProvisioningOutputPayload = MAPPER.convertValue(request.getConfirmProvisioning(), ConfirmProvisioningOutputPayload.class);

        String resourcePath = prepareConfirmProvisioningResourcePath(applicationProperties.getConfirmProvisioningPath(), request.getvProvisionedTokenID());
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String outputPayload = MAPPER.writeValueAsString(confirmProvisioningOutputPayload);
        String xPayToken = null;
        xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryStringApiKey,outputPayload,request.getSharedSecret());
        String xRequestId = generateXRequestID();

        ResponseEntity<String> response = null;
        try {
            //TODO SAVE EVENT AS ENROLLDEVICE
            response = confirmProvisioningClient.putConfirmProvisioning(
                    request.getvProvisionedTokenID(),
                    request.getApiKey(),
                    xPayToken,
                    xRequestId,
                    outputPayload);
        } catch (FeignException e) {
            confirmProvisioningRepository.save(ConfirmProvisioningModel.builder()
                    ._id(xRequestId)
                    .createdAt(LocalDateTime.now())
                    .modelOfErrorVtsCall(MAPPER.writeValueAsString(e))
                    .confirmProvisioningOutputPayload(toJson(confirmProvisioningOutputPayload))
                    .build());
            throw e;

        } catch (Exception e) {
            confirmProvisioningRepository.save(ConfirmProvisioningModel.builder()
                    ._id(xRequestId)
                    .createdAt(LocalDateTime.now())
                    .confirmProvisioningOutputPayload(toJson(confirmProvisioningOutputPayload))
                    .modelOfGenericInternalError(e.getMessage())
                    .build());
            throw e;
        }
        confirmProvisioningRepository.save(ConfirmProvisioningModel.builder()
                ._id(xRequestId)
                .createdAt(LocalDateTime.now())
                .confirmProvisioningOutputPayload(toJson(confirmProvisioningOutputPayload))
                .build());

        logger.info("_______ END putConfirmProvisioning __________ ");

        return response;
    }

    private String prepareConfirmProvisioningResourcePath(String resourcePath, String vProvisionedTokenID){
        resourcePath = resourcePath.replace("{vProvisionedTokenID}",vProvisionedTokenID);
        return resourcePath;
    }
}

