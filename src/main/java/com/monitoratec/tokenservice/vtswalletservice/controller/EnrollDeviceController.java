package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.common.CommonController;
import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.DasDeviceEnrollmentPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.delete.DeleteDeviceTokenBindingPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.EnrollDevicePayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind.EnrollDeviceTokenBindPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.EnrollDeviceService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
public class EnrollDeviceController implements CommonController {

    private static final Logger logger = LoggerFactory.getLogger(EnrollDeviceController.class);
    @Autowired
    private EnrollDeviceService enrollDeviceService;

    @PutMapping("/deviceEnrollment")
    public ResponseEntity<String> putDeviceEnrollment (@RequestBody EnrollDevicePayload request) throws IOException, XPayTokenException {
        logger.info("_______ START putDeviceEnrollMent __________ ");
        ResponseEntity<String> response = enrollDeviceService.putDeviceEnrollmentToVtsClient(request);
        return response;
    }

    @PostMapping("/deviceEnrollment/provisionedTokens")
    public ResponseEntity<String> postTokenBindToEnrolledDevice (@RequestBody EnrollDeviceTokenBindPayload request) throws XPayTokenException, GenericSecurityException, JsonProcessingException, NoSuchAlgorithmException, JOSEException {
        logger.info("_______ START postTokenBindToEnrolledDevice __________ ");
        ResponseEntity<String> response = enrollDeviceService.postTokenBindToEnrolledDevice(request);
        return response;
    }

    @DeleteMapping("deviceEnrollment/provisionedTokens")
    public ResponseEntity<String> deleteDeviceTokenBinding (@RequestBody DeleteDeviceTokenBindingPayload request) throws XPayTokenException, GenericSecurityException, JsonProcessingException{
        logger.info("_______ START deleteDeviceTokenBinding __________ ");
        ResponseEntity<String> response = enrollDeviceService.deleteDeviceTokenBinding(request);
        return response;
    }

    @PostMapping("deviceEnrollment/DasDeviceEnrollment")
    public ResponseEntity<String> postDasDeviceEnrollment (@RequestBody DasDeviceEnrollmentPayload request) throws GenericSecurityException, JsonProcessingException{
        logger.info("_______ START postDasDeviceEnrollment __________ ");
        ResponseEntity<String> response = enrollDeviceService.postDasDeviceEnrollment(request);
        return response;
    }
}
