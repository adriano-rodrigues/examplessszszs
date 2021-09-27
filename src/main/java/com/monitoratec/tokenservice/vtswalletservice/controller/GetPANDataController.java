package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.common.CommonController;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata.PanDataPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.GetPANDataService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class GetPANDataController implements CommonController {
    private static final Logger logger = LoggerFactory.getLogger(GetPANDataController.class);

    @Autowired
    private GetPANDataService getPANDataService;

    @PostMapping("/panData")
    public ResponseEntity<String> getPanData (@RequestBody PanDataPayload request) throws NoSuchAlgorithmException, JOSEException, XPayTokenException, JsonProcessingException {
        logger.info("_______ getPanData __________ ");
        ResponseEntity<String> response = getPANDataService.getPanData(request);
        return response;
    }

}
