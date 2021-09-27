package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das;

import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.das.CertUsage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VisaCertReferenceList {
    @NotNull
    private String getvCertificateID;

    @NotNull
    private CertUsage certUsage;
}
