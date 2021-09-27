package com.monitoratec.tokenservice.vtswalletservice.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConfirmProvisioningModel {
    @Id@JsonProperty("id")
    private String _id;

    private String confirmProvisioningOutputPayload;

    private LocalDateTime createdAt;

    private String modelOfSuccessVtsCall;

    private String modelOfErrorVtsCall;

    private String modelOfGenericInternalError;
}
