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
public class ProvisionTokenProvisionedTokensForAPaymentInstrumentModel {
    public ProvisionTokenProvisionedTokensForAPaymentInstrumentModel(String _id, LocalDateTime createdAt, String provisionedTokensForAPaymentInstrumentOutputPayload, String modelOfSuccessFeignCall, String modelOfErrorVtsCall, String modelOfGenericInternalError) {
        this._id = _id;
        this.createdAt = LocalDateTime.now();
        this.provisionedTokensForAPaymentInstrumentOutputPayload = provisionedTokensForAPaymentInstrumentOutputPayload;
        this.modelOfSuccessVtsCall = modelOfSuccessFeignCall;
        this.modelOfErrorVtsCall = modelOfErrorVtsCall;
        this.modelOfGenericInternalError = modelOfGenericInternalError;
    }

    @Id
    @JsonProperty("id")
    private String _id;

    private LocalDateTime createdAt;


    private String provisionedTokensForAPaymentInstrumentOutputPayload;

    private String modelOfSuccessVtsCall;

    private String modelOfErrorVtsCall;

    private String modelOfGenericInternalError;

}
