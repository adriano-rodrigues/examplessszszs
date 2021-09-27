package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProvisionedTokensForAPanPayload {

    @NotNull(message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;

    private ProvisionedTokensForAPan provisionedTokensForAPan;

}
