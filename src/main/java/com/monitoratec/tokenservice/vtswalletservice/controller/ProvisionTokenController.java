package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.common.CommonController;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan.ProvisionedTokensForAPanPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument.ProvisionedTokensForAPaymentInstrumentPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.ProvisionedTokensForAPanService;
import com.monitoratec.tokenservice.vtswalletservice.service.ProvisionedTokensForAPaymentInstrumentService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@RestController
public class ProvisionTokenController implements CommonController {
    private static final Logger logger = LoggerFactory.getLogger(ProvisionTokenController.class);

    @Autowired
    private ProvisionedTokensForAPanService provisionedTokensForAPanService;

    @Autowired
    private ProvisionedTokensForAPaymentInstrumentService provisionedTokensForAPaymentInstrumentService;

    @PostMapping("provisionToken/provisionedTokensForAPan")
    public ResponseEntity postProvisionedTokensForAPan(@RequestBody ProvisionedTokensForAPanPayload request) throws XPayTokenException, JsonProcessingException {
        logger.info("_______ START postProvisionedTokensForAPan __________ ");
        ResponseEntity<String> response = null;
        response = provisionedTokensForAPanService.postProvisionedTokensForAPanToVtsClient(request);

        return response;
    }

    @PostMapping("provisionToken/provisionedTokensForAPaymentInstrument")
    public ResponseEntity<String> postProvisionedTokensForAPaymentInstrument(@RequestBody ProvisionedTokensForAPaymentInstrumentPayload request) throws XPayTokenException, JsonProcessingException, NoSuchAlgorithmException, JOSEException, ParseException {
        logger.info("_______ START postProvisionedTokensForAPaymentInstrument __________ ");

        ResponseEntity<String> response = null;
        response = provisionedTokensForAPaymentInstrumentService.postProvisionedTokensForAPaymentInstrumentToVtsClient(request);

        return response;
    }
}
