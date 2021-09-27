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
public class DeviceInfo {

    @NotNull
    private String deviceType;
    private String productVersion;
    private String phoneNumber;
    private String productCode;
    private String osVersion;
    @NotNull
    private String osType;
    private String deviceModel;
    private String deviceManufacturer;
    @NotNull
    private String deviceName;
    private String deviceBrand;

}
