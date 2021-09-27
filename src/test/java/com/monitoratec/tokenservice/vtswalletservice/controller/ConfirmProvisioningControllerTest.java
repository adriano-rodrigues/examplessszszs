package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.constant.confirm.provisioning.ProvisioningStatus;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning.ConfirmProvisioning;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.confirm.provisioning.ConfirmProvisioningPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ConfirmProvisioningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper MAPPER = new ObjectMapper();

    private URI uri;
    private String apiKey;
    private String sharedSecret;
    private String wrongApiKey;
    private String wrongSharedSecret;
    private String vProvisionedTokenID;

    public ConfirmProvisioningControllerTest() throws URISyntaxException {
        uri = new URI("/confirmProvisioning");
        apiKey = "D740AEMAPUJOUQCEOR8O21qji0tMVF7qteDkuiea-Mad882is";
        sharedSecret = "nY8rzX-9Sq09WCUq23-JXqNdyhLWXxPA@LvFulK@";
        wrongApiKey = apiKey + "1";
        wrongSharedSecret = sharedSecret + "1";
        vProvisionedTokenID = "adc24340ad9511e4bec7e2fd83f838a9";
    }

    @DisplayName("Check endpoint response")
    @Test
    public void WhenSendRequestToEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .put(uri))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check request builder")
    @Test
    public void TestRequestBuilder() throws Exception {
        ConfirmProvisioningPayload confirmProvisioningPayload = ConfirmProvisioningPayload
            .builder()
            .apiKey(apiKey)
            .confirmProvisioning(ConfirmProvisioning
                .builder()
                .provisioningStatus(ProvisioningStatus.SUCCESS)
                .build())
            .sharedSecret(sharedSecret)
            .vProvisionedTokenID(vProvisionedTokenID)
            .build();

        RequestBuilder request = MockMvcRequestBuilders
            .put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(confirmProvisioningPayload)
            );
    }

    @DisplayName("Check auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreCorrectAuthInVTS() throws Exception {
        ConfirmProvisioningPayload confirmProvisioningPayload = ConfirmProvisioningPayload
            .builder()
            .apiKey(apiKey)
            .confirmProvisioning(ConfirmProvisioning
                .builder()
                .provisioningStatus(ProvisioningStatus.SUCCESS)
                .build())
            .sharedSecret(sharedSecret)
            .vProvisionedTokenID(vProvisionedTokenID)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
            .put(uri)
            .content(MAPPER.writeValueAsString(confirmProvisioningPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTS() throws Exception {
        ConfirmProvisioningPayload confirmProvisioningPayload = ConfirmProvisioningPayload
            .builder()
            .apiKey(wrongApiKey)
            .confirmProvisioning(ConfirmProvisioning
                .builder()
                .provisioningStatus(ProvisioningStatus.SUCCESS)
                .build())
            .sharedSecret(wrongSharedSecret)
            .vProvisionedTokenID(vProvisionedTokenID)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(MAPPER.writeValueAsString(confirmProvisioningPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401));
    }

}
