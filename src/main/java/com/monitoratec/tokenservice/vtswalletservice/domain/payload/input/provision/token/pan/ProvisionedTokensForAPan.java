package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan;

import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.*;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.RiskData;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.ChannelSecurityContext;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.ProvisionToken;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.SsdData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProvisionedTokensForAPan extends ProvisionToken{

    @NotNull
    @Size(max = 5)
    @ApiModelProperty(example = "pt-BR")
    private String locale;
    private PassCode passCode;
    @NotNull
    private PanSource panSource;
    private ConsumerEntryMode consumerEntryMode;
    @NotNull
    @Size(max = 8192)
    private String encPaymentInstrument;

    public ProvisionedTokensForAPan() {
        super();
    }

    public ProvisionedTokensForAPan(SsdData ssdData, @Size(max = 25) String location, @Size(max = 15) String ipv4Address, AccountType accountType, @NotNull @Size(max = 36) String clientAppID, PlatformType platformType, @Size(max = 24) String clientDeviceID, @Size(max = 4096) String issuerAuthCode, String locationSource, @NotNull ProtectionType protectionType, String encRiskDataInfo, @NotNull List<String> presentationType, @Size(max = 512) String encryptionMetaData, @Size(max = 24) String clientWalletAccountID, ChannelSecurityContext channelSecurityContext, @Size(max = 48) @Email String clientWalletAccountEmailAddress, @NotNull @Size(max = 48) String clientWalletAccountEmailAddressHash, List<RiskData> riskData) {
        super(ssdData, location, ipv4Address, accountType, clientAppID, platformType, clientDeviceID, issuerAuthCode, locationSource, protectionType, encRiskDataInfo, presentationType, encryptionMetaData, clientWalletAccountID, channelSecurityContext, clientWalletAccountEmailAddress, clientWalletAccountEmailAddressHash, riskData);
    }
}
