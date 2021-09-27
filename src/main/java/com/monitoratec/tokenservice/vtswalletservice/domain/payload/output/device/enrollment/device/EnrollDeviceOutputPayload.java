package com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.device.enrollment.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.ChannelSecurityContext;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.DeviceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollDeviceOutputPayload {
    private ChannelSecurityContext channelSecurityContext;
    @NotBlank
    @NotNull
    private DeviceInfo deviceInfo;
    private String deviceInitParams;
}
