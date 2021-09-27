package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind;

import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.tokenbind.LocationSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceRiskDataInfo {
    private String location;
    private String ipAddress;
    private LocationSource locationSource;
}
