package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.AccountType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.PlatformType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.ProtectionType;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.RiskData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvisionToken {

    private SsdData ssdData;
    @Size(max = 25)
    private String location;
    @Size(max = 15)
    private String ip4Address;
    @Size(max = 10240)
    private AccountType accountType;
    @NotNull
    @Size(max = 36)
    private String clientAppID;
    private PlatformType platformType;
    @Size(max = 24)
    @ApiModelProperty(example = "1374184250012220225")
    private String clientDeviceID;
    @Size(max = 4096)
    private String issuerAuthCode;
    private String locationSource;
    @NotNull
    private ProtectionType protectionType;

    private List<RiskData> riskData;

    private String encRiskDataInfo;
    @NotNull
    @ApiModelProperty(dataType = "List", example = "[NFC-HCE]")
    private List<String> presentationType;
    @Size(max = 512)
    private String encryptionMetaData;
    @Size(max = 24)
    private String clientWalletAccountID;
    private ChannelSecurityContext channelSecurityContext;
    @Size(max = 48)
    @Email
    private String clientWalletAccountEmailAddress;
    @NotNull
    @Size(max = 48)
    private String clientWalletAccountEmailAddressHash;

    public ProvisionToken() {
    }

    public ProvisionToken(SsdData ssdData, String location, String ipv4Address, AccountType accountType, String clientAppID, PlatformType platformType, String clientDeviceID, String issuerAuthCode, String locationSource, ProtectionType protectionType, String encRiskDataInfo, List<String> presentationType, String encryptionMetaData, String clientWalletAccountID, ChannelSecurityContext channelSecurityContext, String clientWalletAccountEmailAddress, String clientWalletAccountEmailAddressHash, List<RiskData> riskData) {
        this.riskData = riskData;
        this.ssdData = ssdData;
        this.location = location;
        this.ip4Address = ipv4Address;
        this.accountType = accountType;
        this.clientAppID = clientAppID;
        this.platformType = platformType;
        this.clientDeviceID = clientDeviceID;
        this.issuerAuthCode = issuerAuthCode;
        this.locationSource = locationSource;
        this.protectionType = protectionType;
        this.encRiskDataInfo = encRiskDataInfo;
        this.presentationType = presentationType;
        this.encryptionMetaData = encryptionMetaData;
        this.clientWalletAccountID = clientWalletAccountID;
        this.channelSecurityContext = channelSecurityContext;
        this.clientWalletAccountEmailAddress = clientWalletAccountEmailAddress;
        this.clientWalletAccountEmailAddressHash = clientWalletAccountEmailAddressHash;
    }
}
