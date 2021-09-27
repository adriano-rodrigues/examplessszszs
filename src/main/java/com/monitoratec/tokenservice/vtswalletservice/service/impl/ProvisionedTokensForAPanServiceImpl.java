package com.monitoratec.tokenservice.vtswalletservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.client.provisiontoken.ProvisionedTokensForAPanClient;
import com.monitoratec.tokenservice.vtswalletservice.common.ApplicationProperties;
import com.monitoratec.tokenservice.vtswalletservice.common.XPayTokenGenerator;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan.ProvisionedTokensForAPanPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.provision.token.pan.ProvisionedTokensForAPanOutputPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.ProvisionedTokensForAPanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.monitoratec.tokenservice.vtswalletservice.util.WalletServiceUtil.generateXRequestID;

@Service
public class ProvisionedTokensForAPanServiceImpl implements ProvisionedTokensForAPanService {

    private static Logger logger = LoggerFactory.getLogger(ProvisionedTokensForAPanServiceImpl.class);
    @Autowired
    private ProvisionedTokensForAPanClient provisionedTokensForAPanClient;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private XPayTokenGenerator xPayTokenGenerator;
    private ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public ResponseEntity postProvisionedTokensForAPanToVtsClient(ProvisionedTokensForAPanPayload request) throws XPayTokenException, JsonProcessingException {
        ProvisionedTokensForAPanOutputPayload provisionedTokensForAPanOutputPayload;
        provisionedTokensForAPanOutputPayload = MAPPER.convertValue(request.getProvisionedTokensForAPan(), ProvisionedTokensForAPanOutputPayload.class);
        String resourcePath = applicationProperties.getProvisionedTokensForAPanPath();
        String queryStringApiKey = applicationProperties.getApiKeyQueryParam()+request.getApiKey();
        String outputPayload = MAPPER.writeValueAsString(provisionedTokensForAPanOutputPayload);
        String xPayToken = null;
        xPayToken = xPayTokenGenerator.generateXpayToken(resourcePath,queryStringApiKey,outputPayload,request.getSharedSecret());
        String xRequestId = generateXRequestID();

        ResponseEntity<String> response = null;
        response = provisionedTokensForAPanClient.postProvisionedTokensForAPan(
            request.getApiKey(),
            xPayToken,
            xRequestId,
            outputPayload);

        logger.info("_______ END postProvisionedTokensForAPan __________ ");

        return response;
    }
}
