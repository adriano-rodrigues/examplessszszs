package com.monitoratec.tokenservice.vtswalletservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning.ConfirmProvisioningPayload;
import org.springframework.http.ResponseEntity;

public interface ConfirmProvisioningService {

    ResponseEntity<String> putConfirmProvisioningToVtsClient(ConfirmProvisioningPayload request) throws XPayTokenException, JsonProcessingException;

}
