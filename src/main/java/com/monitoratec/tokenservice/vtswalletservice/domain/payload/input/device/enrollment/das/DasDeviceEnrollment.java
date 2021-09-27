package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DasDeviceEnrollment {

    @NotNull
    private List<String> deviceCertList;
    private String profileAppID;

    @NotNull
    private DeviceProfile deviceProfile;

    private VisaCertReferenceList visaCertReferenceList;

    @NotNull
    private DeviceInfo deviceInfo;

}
