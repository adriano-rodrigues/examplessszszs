package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingAddress {

    public String line1;
    public String line2;
    public String city;
    public String postalCode;
    public String state;
    public String country;

    @Override
    public String toString() {
        return "{" +
                "'line1':'" + line1 + '\'' +
                ", 'line2':'" + line2 + '\'' +
                ", 'city':'" + city + '\'' +
                ", 'postalCode':'" + postalCode + '\'' +
                ", 'state':'" + state + '\'' +
                ", 'country':'" + country + '\'' +
                '}';
    }
}
