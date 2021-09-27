package com.monitoratec.tokenservice.vtswalletservice.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Getter
public class DeviceEnrollmentModel {
    public DeviceEnrollmentModel(String _id, LocalDateTime createdAt, String enrollDeviceRequestBody, String modelOfSuccessFeignCall, String modelOfErrorFeignCall, String modelOfGenericInternalError) {
        this._id = _id;
        this.createdAt = LocalDateTime.now();
        this.enrollDeviceRequestBody = enrollDeviceRequestBody;
        this.modelOfSuccessVtsCall = modelOfSuccessFeignCall;
        this.modelOfErrorVtsCall = modelOfErrorFeignCall;
        this.modelOfGenericInternalError = modelOfGenericInternalError;
    }

    @Id
    @JsonProperty("id")
    private String _id;

    private LocalDateTime createdAt;

    private String enrollDeviceRequestBody;

    private String modelOfSuccessVtsCall;

    private String modelOfErrorVtsCall;

    private String modelOfGenericInternalError;

}
