package com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.ChannelInfo;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.DeviceCerts;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.VtsCerts;
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
public class ChannelSecurityContext {
    /**
     * (Conditional) The security scheme used to secure communication between the device and the Visa Token Service back-end.
     * It is only applicable for enrolling NFC-SE devices. Depending on the scheme selected, more data may be required.
     * This field is required only if channelInfo is specified.
     * Note
     * For Secure Copy (SCP) encryption schemes, the encryption version and the type are concatenated to create a string
     * in the form of scp_version/type as per Global Platform Amendment A.
     */
    private ChannelInfo channelInfo;

    private String keyData;

    private List<DeviceCerts> deviceCerts;
    @JsonProperty("vtsCerts")
    private List<VtsCerts> vtsCerts;

}
