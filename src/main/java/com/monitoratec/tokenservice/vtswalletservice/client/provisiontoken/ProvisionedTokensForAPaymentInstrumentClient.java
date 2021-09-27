package com.monitoratec.tokenservice.vtswalletservice.client.provisiontoken;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="vtsProvisionedTokensForAPaymentInstrumentPath", url = "${vts.application.cte.baseurl}")
public interface ProvisionedTokensForAPaymentInstrumentClient {
    @RequestMapping(method = RequestMethod.POST, value= "${vts.application.provisionedTokensForAPaymentInstrumentPath}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postProvisionedTokensForAPaymentInstrument(
        @PathVariable("vPanEnrollmentID") String vPanEnrollmentID,
        @RequestParam("apiKey") String apiKey,
        @RequestHeader("x-pay-token") String xPayToken,
        @RequestHeader("x-request-id") String xRequestId,
        @RequestBody String payLoad);
}
