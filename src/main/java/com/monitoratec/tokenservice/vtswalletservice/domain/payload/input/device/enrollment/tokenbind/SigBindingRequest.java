package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SigBindingRequest {
    private Integer nonce;
    private String clientDeviceID;
    private String clientReferenceID;
    private String vProvisionedTokenID;
}
