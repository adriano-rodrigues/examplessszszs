package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConfirmProvisioningPayload {

    @NotNull(message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;
    @NotNull
    @ApiModelProperty(example = "adc24340ad9511e4bec7e2fd83f838a9")
    private String vProvisionedTokenID;

    private ConfirmProvisioning confirmProvisioning;

    public String getvProvisionedTokenID() {
        return vProvisionedTokenID;
    }

    public void setvProvisionedTokenID(String vProvisionedTokenID) {
        this.vProvisionedTokenID = vProvisionedTokenID;
    }
}
