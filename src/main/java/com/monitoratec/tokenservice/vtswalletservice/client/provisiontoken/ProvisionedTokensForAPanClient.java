package com.monitoratec.tokenservice.vtswalletservice.client.provisiontoken;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="vtsProvisionedTokensForAPanClient", url = "${vts.application.cte.baseurl}")
public interface ProvisionedTokensForAPanClient {
    @RequestMapping(method = RequestMethod.POST, value= "${vts.application.provisionedTokensForAPanPath}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postProvisionedTokensForAPan(
        @RequestParam("apiKey") String apiKey,
        @RequestHeader("x-pay-token") String xPayToken,
        @RequestHeader("x-request-id") String xRequestId,
        @RequestBody String payLoad);

    @RequestMapping(method = RequestMethod.POST, value= "${vts.application.provisionedTokensForAEnrolledDevice}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postProvisionedTokensForAEnrolledDevice(
            @PathVariable("vProvisionedTokenID") String vProvisionedTokenID,
            @RequestParam("apiKey") String apiKey,
            @RequestHeader("x-pay-token") String xPayToken,
            @RequestHeader("x-request-id") String xRequestId,
            @RequestBody String payLoad);

    @RequestMapping(method = RequestMethod.DELETE, value= "${vts.application.provisionedTokensForDeleteDeviceTokenBinding}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteDeviceTokenBinding(
        @PathVariable("vProvisionedTokenID") String vProvisionedTokenID,
        @PathVariable("clientDeviceID") String clientDeviceID,
        @RequestParam("apiKey") String apiKey,
        @RequestHeader("x-pay-token") String xPayToken,
        @RequestHeader("x-request-id") String xRequestId,
        @RequestBody String payLoad);
}
