package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device;

import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.device.CertUsage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceCerts {
    private String certFormat = "X509";
    @ApiModelProperty( example = "CONFIDENTIALITY")
    private CertUsage certUsage;
    private String certValue;

}
