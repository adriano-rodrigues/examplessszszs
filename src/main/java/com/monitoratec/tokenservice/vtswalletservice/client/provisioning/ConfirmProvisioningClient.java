package com.monitoratec.tokenservice.vtswalletservice.client.provisioning;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="vtsConfirmProvisioningClient", url = "${vts.application.cte.baseurl}")
public interface ConfirmProvisioningClient {
    @RequestMapping(method = RequestMethod.PUT, value= "${vts.application.confirmProvisioningPath}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> putConfirmProvisioning(
        @PathVariable("vProvisionedTokenID") String vProvisionedTokenID,
        @RequestParam("apiKey") String apiKey,
        @RequestHeader("x-pay-token") String xPayToken,
        @RequestHeader("x-request-id") String xRequestId,
        @RequestBody String payLoad);
}
