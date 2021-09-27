package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PassCode {

    @NotNull
    private String type;
    @NotNull
    @Size(max = 50)
    private String value;
}
