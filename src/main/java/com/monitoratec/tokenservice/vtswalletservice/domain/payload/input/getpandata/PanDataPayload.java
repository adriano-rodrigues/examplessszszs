package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata;

import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.BasePayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PanDataPayload extends BasePayload {
    @NotNull (message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;

    private PanData panData;
}
