package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MerchantId {
    private String aid;
    private String mid;
    private String clientID;
}
