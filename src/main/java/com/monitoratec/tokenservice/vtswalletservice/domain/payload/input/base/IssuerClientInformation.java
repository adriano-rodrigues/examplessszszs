package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class IssuerClientInformation{
    public String source;
    public String clientWalletAccountID;
    public String firstName;
    public String middleName;
    public String lastName;
    public String issuerAccountID;
    public String clientDeviceID;
    public String tokenReferenceID;
    public String paymentAccountReference;
    public String phoneNumber;
    public String contactEmail;
    public String country;
    public String locale;
    public String campaignID;
    public String campaignDescription;
    public String userPersonalID;
}
