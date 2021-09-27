package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollDevice {
    private ChannelSecurityContext channelSecurityContext;
    @NotNull
    private DeviceInfo deviceInfo;
    private String deviceInitParams;

}
