package com.monitoratec.tokenservice.vtswalletservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.DasDeviceEnrollmentPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.delete.DeleteDeviceTokenBindingPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.EnrollDevicePayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind.EnrollDeviceTokenBindPayload;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface EnrollDeviceService {

    ResponseEntity<String> putDeviceEnrollmentToVtsClient(EnrollDevicePayload request) throws IOException, XPayTokenException;

    ResponseEntity<String> postTokenBindToEnrolledDevice(EnrollDeviceTokenBindPayload request) throws GenericSecurityException, JsonProcessingException, XPayTokenException, NoSuchAlgorithmException, JOSEException;

    ResponseEntity<String> deleteDeviceTokenBinding(DeleteDeviceTokenBindingPayload request) throws GenericSecurityException, JsonProcessingException, XPayTokenException;

    ResponseEntity<String> postDasDeviceEnrollment(DasDeviceEnrollmentPayload request) throws GenericSecurityException, JsonProcessingException;

}
