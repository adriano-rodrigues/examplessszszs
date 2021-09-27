package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class EnrollPanPayload {
    @NotNull (message = "API Key should not be empty")
    private String apiKey;
    @NotNull
    private String sharedSecret;

    private EnrollPan enrollPan;

}
