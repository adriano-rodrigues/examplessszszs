package com.monitoratec.tokenservice.vtswalletservice.common;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface CommonController {
    default <T> ResponseEntity<T> createResponseEntity(T t, HttpStatus status, HttpHeaders headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            httpHeaders.addAll(headers);
        }
        httpHeaders.add("X-Request-Id", MDC.get("requestID"));
        return new ResponseEntity<T>(t, httpHeaders, status);
    }
    default  <T> ResponseEntity<T> createResponseEntity(ResponseEntity<T> responseEntity) {
        T body = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
        return createResponseEntity(body, HttpStatus.OK, headers);
    }
}
