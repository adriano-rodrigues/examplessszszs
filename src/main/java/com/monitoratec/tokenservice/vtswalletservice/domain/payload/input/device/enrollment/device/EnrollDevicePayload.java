package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EnrollDevicePayload {

    @NotNull (message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;
    @NotNull
    private String vClientId;

    private EnrollDevice enrollDevice;
}
