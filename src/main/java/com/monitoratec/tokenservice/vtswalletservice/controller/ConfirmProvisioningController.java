package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.common.CommonController;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning.ConfirmProvisioningPayload;
import com.monitoratec.tokenservice.vtswalletservice.service.ConfirmProvisioningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmProvisioningController implements CommonController {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmProvisioningController.class);

    @Autowired
    private ConfirmProvisioningService confirmProvisioningService;

    @PutMapping("/confirmProvisioning")
    public ResponseEntity putConfirmProvisioning (@RequestBody ConfirmProvisioningPayload request) throws XPayTokenException, JsonProcessingException {
        logger.info("_______ START putConfirmProvisioning __________ ");
        ResponseEntity<String> response = null;
        response = confirmProvisioningService.putConfirmProvisioningToVtsClient(request);

        return response;
    }
}
