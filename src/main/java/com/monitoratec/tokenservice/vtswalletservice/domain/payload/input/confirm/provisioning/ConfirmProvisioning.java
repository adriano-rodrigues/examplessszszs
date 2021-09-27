package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning;

import com.monitoratec.tokenservice.vtswalletservice.constant.confirm.provisioning.ProvisioningStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConfirmProvisioning {

    private String api;
    private Boolean reperso;
    private String failureReason;
    private List<ApduReasonCodes> apduReasonCodes;
    @NotNull
    private ProvisioningStatus provisioningStatus;

}
