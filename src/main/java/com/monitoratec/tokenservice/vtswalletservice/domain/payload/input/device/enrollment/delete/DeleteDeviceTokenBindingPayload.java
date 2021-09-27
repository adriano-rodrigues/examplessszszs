package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.delete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteDeviceTokenBindingPayload {

    @NotNull(message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;

    @NotNull
    private String vProvisionedTokenID;

    @NotNull
    private String clientDeviceID;

    public String getvProvisionedTokenID() {
        return vProvisionedTokenID;
    }

    public void setvProvisionedTokenID(String vProvisionedTokenID) {
        this.vProvisionedTokenID = vProvisionedTokenID;
    }

}
