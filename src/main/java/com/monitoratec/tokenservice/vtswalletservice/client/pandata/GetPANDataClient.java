package com.monitoratec.tokenservice.vtswalletservice.client.pandata;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="vtsGetPANDataClient", url = "${vts.application.cte.baseurl}")
public interface GetPANDataClient {

    @RequestMapping(method = RequestMethod.POST, value= "${vts.application.getPanDataPath}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postGetPANData(
            @RequestParam("apiKey") String apiKey,
            @RequestHeader("x-pay-token") String xPayToken,
            String payload);
}
