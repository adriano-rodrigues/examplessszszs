package com.monitoratec.tokenservice.vtswalletservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument.ProvisionedTokensForAPaymentInstrumentPayload;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public interface ProvisionedTokensForAPaymentInstrumentService {

    ResponseEntity<String> postProvisionedTokensForAPaymentInstrumentToVtsClient(ProvisionedTokensForAPaymentInstrumentPayload request) throws XPayTokenException, JsonProcessingException, NoSuchAlgorithmException, JOSEException, ParseException;

}
