package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasDeviceEnrollmentPayload {

    @NotNull(message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;
    @NotNull
    private String vClientID;
    @NotNull
    private String clientDeviceID;

    private DasDeviceEnrollment dasDeviceEnrollment;

}
