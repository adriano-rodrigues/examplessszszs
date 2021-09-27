package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SsdData {

    @Size(max = 20)
    private String sdAID;
    @NotNull
    private List<String> aidArray;
    private String casdcert;
    @Size(max = 2048)
    private String sdScript;
    @NotNull
    @Size(max = 128)
    private String vmpaversion;
    @Size(max = 32)
    private String vcertificateID;
    @NotNull
    @Size(max = 4)
    private String sequenceCounter;
    @NotNull
    @Size(max = 2)
    private String keyVersionNumber;
}
