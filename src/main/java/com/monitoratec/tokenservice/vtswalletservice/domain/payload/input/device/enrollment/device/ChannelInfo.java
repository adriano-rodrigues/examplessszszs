package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public @Data class ChannelInfo {
    @ApiModelProperty( example = "RSA_PKI")
    private String encryptionScheme;
}
