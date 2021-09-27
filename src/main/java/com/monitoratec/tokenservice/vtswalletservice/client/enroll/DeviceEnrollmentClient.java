package com.monitoratec.tokenservice.vtswalletservice.client.enroll;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="vtsDeviceEnrollmentClient", url = "${vts.application.cte.baseurl}")
public interface DeviceEnrollmentClient {

    @RequestMapping(method = RequestMethod.PUT, value= "${vts.application.deviceEnrollmentPath}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> putDeviceEnrollment(
            @PathVariable("vClientId") String vClientId,
            @PathVariable("clientDeviceId") String clientDeviceId,
            @RequestParam("apiKey") String apiKey,
            @RequestHeader("x-pay-token") String xPayToken,
            @RequestHeader("x-request-id") String xRequestId,
            @RequestBody String payLoad);

    @RequestMapping(method = RequestMethod.POST, value= "${vts.application.dasDeviceEnrollmentPath}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postDasDeviceEnrollment(
            @PathVariable("vClientID") String vClientID,
            @PathVariable("clientDeviceID") String clientDeviceID,
            @RequestParam("apiKey") String apiKey,
            @RequestHeader("X-SERVICE-CONTEXT") String xServiceContext,
            @RequestBody String payLoad);
}

