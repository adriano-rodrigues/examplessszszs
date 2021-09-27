package com.monitoratec.tokenservice.vtswalletservice.domain.payload.output.confirm.provisioning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoratec.tokenservice.vtswalletservice.constant.confirm.provisioning.ProvisioningStatus;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning.ApduReasonCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfirmProvisioningOutputPayload {
    private String api;
    private Boolean reperso;
    private String failureReason;
    private List<ApduReasonCodes> apduReasonCodes;
    @NotBlank
    @NotNull
    private ProvisioningStatus provisioningStatus;
}
