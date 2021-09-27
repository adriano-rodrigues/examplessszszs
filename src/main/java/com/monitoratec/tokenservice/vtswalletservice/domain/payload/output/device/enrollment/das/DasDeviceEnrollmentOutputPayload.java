package com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.device.enrollment.das;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.DeviceInfo;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.DeviceProfile;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.VisaCertReferenceList;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasDeviceEnrollmentOutputPayload {
    @NotNull
    private List<String> deviceCertList;
    private String profileAppID;

    @NotNull
    private DeviceProfile deviceProfile;

    private VisaCertReferenceList visaCertReferenceList;

    @NotNull
    private DeviceInfo deviceInfo;
}
