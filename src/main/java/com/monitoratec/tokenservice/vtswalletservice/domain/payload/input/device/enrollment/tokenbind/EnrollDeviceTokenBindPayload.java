package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.tokenbind.PlataformType;
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
public class EnrollDeviceTokenBindPayload {

    @NotNull(message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;

    @NotNull
    private String vProvisionedTokenID;

    private PlataformType plataformType;

    private MerchantId merchantID;

    private SigBindingRequest sigBindingRequest;

    private DeviceRiskDataInfo deviceRiskDataInfo;

    public String getvProvisionedTokenID() {
        return vProvisionedTokenID;
    }

    public void setvProvisionedTokenID(String vProvisionedTokenID) {
        this.vProvisionedTokenID = vProvisionedTokenID;
    }
}
