package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device;

import com.monitoratec.tokenservice.vtswalletservice.constant.DeviceType;
import com.monitoratec.tokenservice.vtswalletservice.constant.OsType;
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
public class DeviceInfo {
    @ApiModelProperty(example = "Android Brand")
    private String deviceBrand;
    @ApiModelProperty(example = "Derived")
    private String deviceIDType;
    @ApiModelProperty(example = "Samsung")
    private String deviceManufacturer;
    @ApiModelProperty(example = "SGH-T999")
    private String deviceModel;
    @ApiModelProperty(example = "TXkgV29ybGQ")
    @NotNull
    private String deviceName;
    @ApiModelProperty(example = "+14087861234")
    private String phoneNumber;
    @ApiModelProperty(example = "PHONE")
    @NotNull
    private DeviceType deviceType;
    @ApiModelProperty(example = "3e89278f175403c4fc4e062")
    @NotNull
    private String hostDeviceID;
    @ApiModelProperty(example = "KTU84M")
    private String osBuildID;
    @ApiModelProperty(example = "ANDROID")
    @NotNull
    private OsType osType;
    @ApiModelProperty(example = "4.4.4")
    private String osVersion;

}
