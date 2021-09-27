package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisaErrorResponse {
    private String code;
    private String severity;
    private String status;
    private String message;
    private String reason;
    private String info;
    private List<ErrorDetail> details;
}
