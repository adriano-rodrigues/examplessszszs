package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.monitoratec.tokenservice.vtswalletservice.common.CommonController;
import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment.EnrollPanPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.EnrollPanService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@RestController
public class EnrollPanController implements CommonController {
    private static final Logger logger = LoggerFactory.getLogger(EnrollDeviceController.class);

    @Autowired
    private EnrollPanService enrollPanService;

    @PostMapping("/panEnrollment")
    public ResponseEntity<String> postPanEnrollment (@RequestBody EnrollPanPayload request) throws XPayTokenException, GenericSecurityException, IOException, JOSEException, NoSuchAlgorithmException, ParseException {
        logger.info("_______ START postPanEnrollment __________ ");
        ResponseEntity<String> response = enrollPanService.postEnrollPanToVtsClient(request);
        return response;
    }

}
