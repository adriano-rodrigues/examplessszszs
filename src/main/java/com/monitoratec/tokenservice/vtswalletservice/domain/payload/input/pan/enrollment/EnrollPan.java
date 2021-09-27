package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.constant.pan.enrollment.PanSource;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.ConsumerEntryMode;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.ChannelSecurityContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollPan {

    @NotNull
    @ApiModelProperty(example = "firstTech")
    private String clientAppID;
    @NotNull
    @ApiModelProperty(example = "AiOxOitsfavni990I6")
    private String clientWalletAccountID;
    @ApiModelProperty(example = "8450744268195285591")
    private String clientDeviceID;
    @NotNull
    @ApiModelProperty(example = "en_US")
    private String locale;
    @NotNull
    private PanSource panSource;

    private ConsumerEntryMode consumerEntryMode;

    @NotNull
    @ApiModelProperty(example = "eyAiY3Z2MiI6IjY1MSIsICJhY2NvdW50TnVtYmVyIjoiNDYyMjk0MzEyNzAyNTgyNCIsICJleHBpcmF0aW9uRGF0ZSI6IAl7IAkJIm1vbnRoIjogIjEyIiwgCQkieWVhciI6ICIyMiIgCX0gfQ")
    private PaymentInstrument paymentInstrument;

    private ChannelSecurityContext channelSecurityContext;
}
