package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base;

import lombok.Data;
import javax.validation.constraints.NotBlank;


public @Data class BasePayload {
    @NotBlank(message = "API Key should not be empty/null")
    private String apiKey;
    @NotBlank(message = "Shared Secret should not be empty/null")
    private String sharedSecret;
}
