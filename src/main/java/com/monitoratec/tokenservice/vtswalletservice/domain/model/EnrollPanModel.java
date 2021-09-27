package com.monitoratec.tokenservice.vtswalletservice.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@Getter
public class EnrollPanModel {
    public EnrollPanModel(String _id, String enrollPanRequestBody, String modelOfSuccessFeignCall, String modelOfErrorFeignCall, String modelOfGenericInternalError, LocalDateTime createdAt) {
        this._id = _id;
        this.enrollPanRequestBody = enrollPanRequestBody;
        this.modelOfSuccessVtsCall = modelOfSuccessFeignCall;
        this.modelOfErrorVtsCall = modelOfErrorFeignCall;
        this.modelOfGenericInternalError = modelOfGenericInternalError;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @JsonProperty("id")
    private String _id;

    private String enrollPanRequestBody;

    private String modelOfSuccessVtsCall;

    private String modelOfErrorVtsCall;

    private String modelOfGenericInternalError;

    private LocalDateTime createdAt;
}
