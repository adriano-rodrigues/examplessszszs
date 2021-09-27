package com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.device.enrollment.tokenbind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.tokenbind.PlataformType;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind.MerchantId;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.tokenbind.SigBindingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollDeviceTokenBindOutputPayload {
    private PlataformType plataformType;

    private MerchantId merchantID;

    private SigBindingRequest sigBindingRequest;

    private String encDeviceRiskDataInfo;
}
