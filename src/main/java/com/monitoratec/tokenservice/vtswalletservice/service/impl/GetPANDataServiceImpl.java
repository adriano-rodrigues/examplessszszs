package com.monitoratec.tokenservice.vtswalletservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.client.pandata.GetPANDataClient;
import com.monitoratec.tokenservice.vtswalletservice.common.ApplicationProperties;
import com.monitoratec.tokenservice.vtswalletservice.common.EncryptionGenerator;
import com.monitoratec.tokenservice.vtswalletservice.common.XPayTokenGenerator;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata.PanDataPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.getpandata.PanDataOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.GetPANDataService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class GetPANDataServiceImpl implements GetPANDataService {
    private static Logger logger = LoggerFactory.getLogger(GetPANDataServiceImpl.class);

    @Autowired
    private GetPANDataClient getPANDataClient;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private XPayTokenGenerator xPayTokenGenerator;
    @Autowired
    private EncryptionGenerator encryptionGenerator;
    private ObjectMapper MAPPER = new ObjectMapper();
    private String encPaymentInstrument;


    @Override
    public ResponseEntity<String> getPanData(PanDataPayload request) throws NoSuchAlgorithmException, JOSEException, XPayTokenException, JsonProcessingException {
        PanDataOutputPayload panDataOutputPayload;
        panDataOutputPayload = MAPPER.convertValue(request, PanDataOutputPayload.class);
        String resourcePath = applicationProperties.getPanDataPath();
        String queryString = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        encPaymentInstrument = encryptionGenerator.encryptData(request.getPanData());
        panDataOutputPayload.setEncPaymentInstrument(encPaymentInstrument);
        String outputPayload = MAPPER.writeValueAsString(panDataOutputPayload);
        String xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryString,outputPayload,request.getSharedSecret());
        ResponseEntity<String> response = null;

        response = getPANDataClient.postGetPANData(request.getApiKey(), xPayToken, outputPayload);

        logger.info("_______ END getPanData __________ ");

        return response;
    }
}
