package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TermsAndConditions {

    @Size(max = 36)
    private String id;
    private String date;
}
