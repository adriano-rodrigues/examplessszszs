package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.device.CertUsage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VtsCerts {
    //(Conditional) The usage of this certificate.
    @ApiModelProperty(example = "CONFIDENTIALITY")
    @JsonProperty("certUsage")
    private CertUsage certUsage;
    @JsonProperty("vCertificateID")
    @NotBlank
    private String vCertificateID;

}
