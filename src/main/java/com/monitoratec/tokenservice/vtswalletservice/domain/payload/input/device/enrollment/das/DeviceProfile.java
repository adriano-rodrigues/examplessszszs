package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceProfile {

    @NotNull
    private boolean hardwareBackKeystoreSupport;
    @NotNull
    private boolean keyAttestationSupport;
}
