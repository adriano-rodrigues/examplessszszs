package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.common.EncryptionGenerator;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.ExpirationDate;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata.PanData;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.getpandata.PanDataPayload;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class GetPANDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private EncryptionGenerator encryptionGenerator;

    private URI uri;
    private String apiKey;
    private String sharedSecret;
    private String wrongApiKey;
    private String wrongSharedSecret;
    private PanData panData;
    private ExpirationDate expirationDate;
    private String last4;

    public GetPANDataControllerTest() throws URISyntaxException, NoSuchAlgorithmException, JsonProcessingException, JOSEException {
        uri = new URI("/panData");
        apiKey = "D740AEMAPUJOUQCEOR8O21qji0tMVF7qteDkuiea-Mad882is";
        sharedSecret = "nY8rzX-9Sq09WCUq23-JXqNdyhLWXxPA@LvFulK@";
        wrongApiKey = apiKey + "1";
        wrongSharedSecret = sharedSecret + "1";
        expirationDate = ExpirationDate.builder().year("2022").month("12").build();
        panData = PanData.builder().accountNumber("4622943127025824").expirationDate(expirationDate).build();
        last4 = "5824";
    }

    @DisplayName("Check endpoint response")
    @Test
    public void WhenSendRequestToEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

 /*   @DisplayName("Check Get PAN Data process")
    @Ignore
    @Test
    public void WhenApiKeyAndSharedSecretAreCorrectAuthInVTS() throws Exception {
        PanDataPayload panDataPayload = PanDataPayload.builder()
            .apiKey(apiKey)
            .sharedSecret(sharedSecret)
            .panData(panData)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(MAPPER.writeValueAsString(panDataPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(200))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.paymentInstrument.last4")
                .value(last4));
    }*/

    @DisplayName("Check dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTS() throws Exception {
        PanDataPayload panDataPayload = PanDataPayload.builder()
            .apiKey(wrongApiKey)
            .sharedSecret(wrongSharedSecret)
            .panData(panData)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(MAPPER.writeValueAsString(panDataPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401));
    }

}
