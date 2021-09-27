package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata;

import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.BillingAddress;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.ExpirationDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PanData {
    private String cvv2;
    private String name;
    private String accountNumber;
    private BillingAddress billingAddress;
    private ExpirationDate expirationDate;
}
