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
public class Provider{
    public String intent;
    public String clientWalletProvider;
    public String clientWalletAccountID;
    public String clientDeviceID;
    public String clientAppID;
    public boolean isIDnV;
    public String issuerAccountID;
    public IssuerClientInformation issuerClientInformation;
    public PassCode passcode;
    public String returnURIType;
    public String returnURI;
    public String issuerTraceID;
    public boolean isTsAndCsAccepted;
}
