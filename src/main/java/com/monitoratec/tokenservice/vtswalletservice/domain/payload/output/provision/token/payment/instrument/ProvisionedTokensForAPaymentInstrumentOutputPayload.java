package com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.provision.token.payment.instrument;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.AccountType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.PlatformType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.ProtectionType;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.RiskData;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.ChannelSecurityContext;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.ProvisionToken;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.SsdData;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument.TermsAndConditions;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvisionedTokensForAPaymentInstrumentOutputPayload extends ProvisionToken{
    private TermsAndConditions termsAndConditions;

    public ProvisionedTokensForAPaymentInstrumentOutputPayload() { super(); }

    public ProvisionedTokensForAPaymentInstrumentOutputPayload(SsdData ssdData, String location, String ipv4Address, AccountType accountType, String clientAppID, PlatformType platformType, String clientDeviceID, String issuerAuthCode, String locationSource, ProtectionType protectionType, String encRiskDataInfo, List<String> presentationType, String encryptionMetaData, String clientWalletAccountID, ChannelSecurityContext channelSecurityContext, String clientWalletAccountEmailAddress, String clientWalletAccountEmailAddressHash, TermsAndConditions termsAndConditions, List<RiskData> riskData) {
        super(ssdData, location, ipv4Address, accountType, clientAppID, platformType, clientDeviceID, issuerAuthCode, locationSource, protectionType, encRiskDataInfo, presentationType, encryptionMetaData, clientWalletAccountID, channelSecurityContext, clientWalletAccountEmailAddress, clientWalletAccountEmailAddressHash, riskData);
    }
}
