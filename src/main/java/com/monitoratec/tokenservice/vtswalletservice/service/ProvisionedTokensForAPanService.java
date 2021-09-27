package com.monitoratec.tokenservice.vtswalletservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan.ProvisionedTokensForAPanPayload;
import org.springframework.http.ResponseEntity;

public interface ProvisionedTokensForAPanService {

    ResponseEntity<String> postProvisionedTokensForAPanToVtsClient(ProvisionedTokensForAPanPayload request) throws XPayTokenException, JsonProcessingException;

}
