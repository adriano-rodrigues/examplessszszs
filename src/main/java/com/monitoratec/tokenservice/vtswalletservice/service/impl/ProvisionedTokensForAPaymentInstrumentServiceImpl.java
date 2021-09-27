package com.monitoratec.tokenservice.vtswalletservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.client.provisiontoken.ProvisionedTokensForAPaymentInstrumentClient;
import com.monitoratec.tokenservice.vtswalletservice.common.ApplicationProperties;
import com.monitoratec.tokenservice.vtswalletservice.common.EncryptionGenerator;
import com.monitoratec.tokenservice.vtswalletservice.common.XPayTokenGenerator;
import com.monitoratec.tokenservice.vtswalletservice.domain.model.ProvisionTokenProvisionedTokensForAPaymentInstrumentModel;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument.ProvisionedTokensForAPaymentInstrumentPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.provision.token.payment.instrument.ProvisionedTokensForAPaymentInstrumentOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.repository.ProvisionTokenProvisionedTokensForAPaymentInstrumentRepository;
import com.monitoratec.tokenservice.vtswalletservice.service.ProvisionedTokensForAPaymentInstrumentService;
import com.nimbusds.jose.JOSEException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import static com.monitoratec.tokenservice.vtswalletservice.util.JSONUtil.toJson;
import static com.monitoratec.tokenservice.vtswalletservice.util.WalletServiceUtil.generateXRequestID;

@Service
public class ProvisionedTokensForAPaymentInstrumentServiceImpl implements ProvisionedTokensForAPaymentInstrumentService {
    private static Logger logger = LoggerFactory.getLogger(ProvisionedTokensForAPanServiceImpl.class);
    @Autowired
    private ProvisionedTokensForAPaymentInstrumentClient provisionedTokensForAPaymentInstrumentClient;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private XPayTokenGenerator xPayTokenGenerator;
    @Autowired
    private EncryptionGenerator encryptionGenerator;
    @Autowired
    private ProvisionTokenProvisionedTokensForAPaymentInstrumentRepository provisionTokenProvisionedTokensForAPaymentInstrumentRepository;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public ResponseEntity postProvisionedTokensForAPaymentInstrumentToVtsClient(ProvisionedTokensForAPaymentInstrumentPayload request) throws XPayTokenException, JsonProcessingException, NoSuchAlgorithmException, JOSEException, ParseException {
        ProvisionedTokensForAPaymentInstrumentOutputPayload provisionedTokensForAPaymentInstrumentOutputPayload;
        provisionedTokensForAPaymentInstrumentOutputPayload = MAPPER.convertValue(request.getProvisionedTokensForAPaymentInstrument(), ProvisionedTokensForAPaymentInstrumentOutputPayload.class);

        String resourcePath = prepareProvisionedTokensForAPaymentInstrument(applicationProperties.getProvisionedTokensForAPaymentInstrument(), request.getvPanEnrollmentID());
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String encRiskDataInfo = encryptionGenerator.encryptData(request.getProvisionedTokensForAPaymentInstrument().getRiskData());
        provisionedTokensForAPaymentInstrumentOutputPayload.setEncRiskDataInfo(encRiskDataInfo);
        provisionedTokensForAPaymentInstrumentOutputPayload.setRiskData(null);
        String outputPayload = MAPPER.writeValueAsString(provisionedTokensForAPaymentInstrumentOutputPayload);
        String xPayToken = null;
        xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryStringApiKey,outputPayload,request.getSharedSecret());
        String xRequestId = generateXRequestID();

        ResponseEntity<String> response = null;
        try {
            response = provisionedTokensForAPaymentInstrumentClient.postProvisionedTokensForAPaymentInstrument(
                    request.getvPanEnrollmentID(),
                    request.getApiKey(),
                    xPayToken,
                    xRequestId,
                    outputPayload);
        }catch (FeignException e){
            provisionTokenProvisionedTokensForAPaymentInstrumentRepository.save(ProvisionTokenProvisionedTokensForAPaymentInstrumentModel.builder()
                    ._id(xRequestId)
                    .modelOfErrorVtsCall(MAPPER.writeValueAsString(e))
                    .provisionedTokensForAPaymentInstrumentOutputPayload(toJson(provisionedTokensForAPaymentInstrumentOutputPayload))
                    .build());
            throw e;
        }catch (Exception e){
            provisionTokenProvisionedTokensForAPaymentInstrumentRepository.save(ProvisionTokenProvisionedTokensForAPaymentInstrumentModel.builder()
                    ._id(xRequestId)
                    .provisionedTokensForAPaymentInstrumentOutputPayload(toJson(provisionedTokensForAPaymentInstrumentOutputPayload))
                    .modelOfGenericInternalError(e.getMessage())
                    .build());
            throw e;
        }
        provisionTokenProvisionedTokensForAPaymentInstrumentRepository.save(ProvisionTokenProvisionedTokensForAPaymentInstrumentModel.builder()
                ._id(xRequestId)
                .provisionedTokensForAPaymentInstrumentOutputPayload(toJson(provisionedTokensForAPaymentInstrumentOutputPayload))
                .modelOfSuccessVtsCall(toJson(response))
                .build());
        logger.info("_______ END provisionedTokensForAPaymentInstrument __________ ");

        return response;
    }

    private String prepareProvisionedTokensForAPaymentInstrument(String resourcePath, String vPanEnrollmentID){
        resourcePath = resourcePath.replace("{vPanEnrollmentID}",vPanEnrollmentID);
        return resourcePath;
    }
}
