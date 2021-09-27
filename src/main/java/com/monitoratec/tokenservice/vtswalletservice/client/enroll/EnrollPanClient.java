package com.monitoratec.tokenservice.vtswalletservice.client.enroll;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="vtsEnrollPanClient", url = "${vts.application.cte.baseurl}")
public interface EnrollPanClient {

    @RequestMapping(method = RequestMethod.POST, value= "${vts.application.enrollPan}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postEnrollPan(
            @RequestParam("apiKey") String apiKey,
            @RequestHeader("x-pay-token") String xPayToken,
            @RequestHeader("x-request-id") String xRequestId,
            @RequestBody String payLoad);

}
