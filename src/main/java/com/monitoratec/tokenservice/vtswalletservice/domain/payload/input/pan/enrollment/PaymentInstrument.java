package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.BillingAddress;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.ExpirationDate;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentInstrument {
    public String accountNumber;
    public String cvv2;
    public ExpirationDate expirationDate;
    public String name;
    public BillingAddress billingAddress;
    public Provider provider;

    @Override
    public String toString() {
        return "{" +
                "'accountNumber':'" + accountNumber + '\'' +
                ", 'cvv2':'" + cvv2 + '\'' +
                ", 'expirationDate':" + expirationDate +
                ", 'name':'" + name + '\'' +
                ", 'billingAddress':" + billingAddress +
                ", 'provider':" + provider +
                '}';
    }
}
