package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProvisionedTokensForAPaymentInstrumentPayload {
    @NotNull(message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;
    @NotNull
    private String vPanEnrollmentID;

    private ProvisionedTokensForAPaymentInstrument provisionedTokensForAPaymentInstrument;

    public String getvPanEnrollmentID() {
        return vPanEnrollmentID;
    }

    public void setvPanEnrollmentID(String vPanEnrollmentID) {
        this.vPanEnrollmentID = vPanEnrollmentID;
    }
}
