package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApduReasonCodes {
    private String apduLine;
    private String reasonCode;
}
