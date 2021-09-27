package com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.pan.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.tokenbind.PlataformType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.ConsumerEntryMode;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.ChannelSecurityContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollPanOutputPayload {
    @NotBlank
    @NotNull
    public String clientWalletAccountID;
    private ConsumerEntryMode consumerEntryMode;
    public String clientDeviceID;
    public PlataformType platformType;
    public ChannelSecurityContext channelSecurityContext;
    @NotBlank
    @NotNull
    public String locale;
    @NotBlank
    @NotNull
    public String clientAppID;
    @NotBlank
    @NotNull
    public String encPaymentInstrument;
    @NotBlank
    @NotNull
    public String panSource;
    public Passcode passcode;
}
