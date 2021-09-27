package com.monitoratec.tokenservice.vtswalletservice.service;

import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment.EnrollPanPayload;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public interface EnrollPanService {

    ResponseEntity<String> postEnrollPanToVtsClient(EnrollPanPayload request) throws XPayTokenException, GenericSecurityException, IOException, JOSEException, NoSuchAlgorithmException, ParseException;

}
