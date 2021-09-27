package com.monitoratec.tokenservice.vtswalletservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata.PanDataPayload;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

public interface GetPANDataService {

    ResponseEntity<String> getPanData (PanDataPayload request) throws NoSuchAlgorithmException, JOSEException, XPayTokenException, JsonProcessingException;
}
