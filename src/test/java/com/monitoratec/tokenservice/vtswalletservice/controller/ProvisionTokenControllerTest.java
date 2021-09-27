package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.AccountType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.PanSource;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.PlatformType;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.ProtectionType;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan.ProvisionedTokensForAPan;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.pan.ProvisionedTokensForAPanPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument.ProvisionedTokensForAPaymentInstrument;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.provision.token.payment.instrument.ProvisionedTokensForAPaymentInstrumentPayload;
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
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ProvisionTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper MAPPER = new ObjectMapper();

    private URI uriProvisionedTokensForAPanEndpoint;
    private URI uriProvisionedTokensForAPaymentInstrumentEndpoint;
    private String apiKey;
    private String sharedSecret;
    private String wrongApiKey;
    private String wrongSharedSecret;
    private String vPanEnrollmentID;
    private List<String> presentationType;
    private ProvisionedTokensForAPan provisionedTokensForAPan;
    private ProvisionedTokensForAPaymentInstrument provisionedTokensForAPaymentInstrument;

    public ProvisionTokenControllerTest() throws URISyntaxException {
        uriProvisionedTokensForAPanEndpoint = new URI("/provisionToken/provisionedTokensForAPan");
        uriProvisionedTokensForAPaymentInstrumentEndpoint = new URI("/provisionToken/provisionedTokensForAPaymentInstrument");
        apiKey = "D740AEMAPUJOUQCEOR8O21qji0tMVF7qteDkuiea-Mad882is";
        sharedSecret = "nY8rzX-9Sq09WCUq23-JXqNdyhLWXxPA@LvFulK@";
        wrongApiKey = apiKey + "1";
        wrongSharedSecret = sharedSecret + "1";
        vPanEnrollmentID = "d49de394c0c2027a34731dc36f9f2b01";
        presentationType = new ArrayList<>();
        presentationType.add("NFC-HCE");

        provisionedTokensForAPan = new ProvisionedTokensForAPan();
        provisionedTokensForAPan.setClientDeviceID("1374184250012220225");
        provisionedTokensForAPan.setClientWalletAccountEmailAddressHash("test");
        provisionedTokensForAPan.setClientWalletAccountID("test");
        provisionedTokensForAPan.setEncPaymentInstrument("test");
        provisionedTokensForAPan.setLocale("pt-BR");
        provisionedTokensForAPan.setPanSource(PanSource.ONFILE);
        provisionedTokensForAPan.setPresentationType(presentationType);
        provisionedTokensForAPan.setProtectionType(ProtectionType.SOFTWARE);

        provisionedTokensForAPaymentInstrument = new ProvisionedTokensForAPaymentInstrument();
        provisionedTokensForAPaymentInstrument.setAccountType(AccountType.WALLET);
        provisionedTokensForAPaymentInstrument.setClientAppID("firstTech");
        provisionedTokensForAPaymentInstrument.setPlatformType(PlatformType.ANDROID);
        provisionedTokensForAPaymentInstrument.setClientDeviceID("8450744268195285591");
        provisionedTokensForAPaymentInstrument.setProtectionType(ProtectionType.SOFTWARE);
        provisionedTokensForAPaymentInstrument.setPresentationType(presentationType);
        provisionedTokensForAPaymentInstrument.setClientWalletAccountID("AiOxOitsfavni990I6");
        provisionedTokensForAPaymentInstrument.setClientWalletAccountEmailAddress("adriano.rodrigues@monitoratec.com.br");
        provisionedTokensForAPaymentInstrument.setClientWalletAccountEmailAddressHash("YWRyaWFuby5yb2RyaWd1ZXNAbW9uaXRvcmF0ZWMuY29tLmJy");

    }

    @DisplayName("Check Provisioned Tokens For A Pan endpoint response")
    @Test
    public void WhenSendRequestToProvisionedTokensForAPanEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPanEndpoint))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check Provisioned Tokens For A Pan endpoint Simple Request")
    @Test
    public void TestSimpleRequestToProvisionedTokensForAPanEndpoint() throws Exception {
        ProvisionedTokensForAPanPayload provisionedTokensForAPanPayload = new ProvisionedTokensForAPanPayload();
        provisionedTokensForAPanPayload.setApiKey(apiKey);
        provisionedTokensForAPanPayload.setSharedSecret(sharedSecret);
        provisionedTokensForAPanPayload.setProvisionedTokensForAPan(provisionedTokensForAPan);

        RequestBuilder request = MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPanEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(provisionedTokensForAPanPayload)
            );
    }

    @DisplayName("Check Provisioned Tokens For A Pan endpoint auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreCorrectAuthInVTSWithProvisionedTokensForAPanEndpoint() throws Exception {
        ProvisionedTokensForAPanPayload provisionedTokensForAPanPayload = new ProvisionedTokensForAPanPayload();
        provisionedTokensForAPanPayload.setApiKey(apiKey);
        provisionedTokensForAPanPayload.setSharedSecret(sharedSecret);
        provisionedTokensForAPanPayload.setProvisionedTokensForAPan(provisionedTokensForAPan);

        mockMvc.perform(MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPanEndpoint)
            .content(MAPPER.writeValueAsString(provisionedTokensForAPanPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));

    }

    @DisplayName("Check Provisioned Tokens For A Pan endpoint dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTSWithProvisionedTokensForAPanEndpoint() throws Exception {
        ProvisionedTokensForAPanPayload provisionedTokensForAPanPayload = new ProvisionedTokensForAPanPayload();
        provisionedTokensForAPanPayload.setApiKey(wrongApiKey);
        provisionedTokensForAPanPayload.setSharedSecret(wrongSharedSecret);
        provisionedTokensForAPanPayload.setProvisionedTokensForAPan(provisionedTokensForAPan);

        mockMvc.perform(MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPanEndpoint)
            .content(MAPPER.writeValueAsString(provisionedTokensForAPanPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401));
    }

    @DisplayName("Check Provisioned Tokens For A Payment Instrument endpoint response")
    @Test
    public void WhenSendRequestToProvisionedTokensForAPaymentInstrumentEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPaymentInstrumentEndpoint))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check Provisioned Tokens For A Payment Instrument simple request")
    @Test
    public void TestSimpleRequestToProvisionedTokensForAPaymentInstrumentEndpoint() throws Exception {
        ProvisionedTokensForAPaymentInstrumentPayload provisionedTokensForAPaymentInstrumentPayload = new ProvisionedTokensForAPaymentInstrumentPayload();
        provisionedTokensForAPaymentInstrumentPayload.setApiKey(apiKey);
        provisionedTokensForAPaymentInstrumentPayload.setSharedSecret(sharedSecret);
        provisionedTokensForAPaymentInstrumentPayload.setProvisionedTokensForAPaymentInstrument(provisionedTokensForAPaymentInstrument);

        RequestBuilder request = MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPaymentInstrumentEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(provisionedTokensForAPaymentInstrumentPayload)
            );
    }

 /*   @DisplayName("Check Provisioned Tokens For A Payment Instrument endpoint auth in VTS")
    @Ignore
    @Test
    public void WhenApiKeyAndSharedSecretAreCorrectAuthInVTSWithProvisionedTokensForAPaymentInstrumentEndpoint() throws Exception {
        ProvisionedTokensForAPaymentInstrumentPayload provisionedTokensForAPaymentInstrumentPayload = new ProvisionedTokensForAPaymentInstrumentPayload();
        provisionedTokensForAPaymentInstrumentPayload.setApiKey(apiKey);
        provisionedTokensForAPaymentInstrumentPayload.setSharedSecret(sharedSecret);
        provisionedTokensForAPaymentInstrumentPayload.setvPanEnrollmentID(vPanEnrollmentID);
        provisionedTokensForAPaymentInstrumentPayload.setProvisionedTokensForAPaymentInstrument(provisionedTokensForAPaymentInstrument);

        mockMvc.perform(MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPaymentInstrumentEndpoint)
            .content(MAPPER.writeValueAsString(provisionedTokensForAPaymentInstrumentPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(403));

    }*/

    @DisplayName("Check Provisioned Tokens For A PaymentInstrument endpoint dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTSWithProvisionedTokensForAPaymentInstrumentEndpoint() throws Exception {
        ProvisionedTokensForAPaymentInstrumentPayload provisionedTokensForAPaymentInstrumentPayload = new ProvisionedTokensForAPaymentInstrumentPayload();
        provisionedTokensForAPaymentInstrumentPayload.setApiKey(wrongApiKey);
        provisionedTokensForAPaymentInstrumentPayload.setSharedSecret(wrongSharedSecret);
        provisionedTokensForAPaymentInstrumentPayload.setvPanEnrollmentID(vPanEnrollmentID);
        provisionedTokensForAPaymentInstrumentPayload.setProvisionedTokensForAPaymentInstrument(provisionedTokensForAPaymentInstrument);

        mockMvc.perform(MockMvcRequestBuilders
            .post(uriProvisionedTokensForAPaymentInstrumentEndpoint)
            .content(MAPPER.writeValueAsString(provisionedTokensForAPaymentInstrumentPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401));
    }

/*    @DisplayName("Check Provisioned Tokens For A Payment Instrument process error 403")
    @Test
    public void WhenSendProvisionedTokensForAPaymentEndpointItReturnsOk() throws Exception {
        ProvisionedTokensForAPaymentInstrumentPayload provisionedTokensForAPaymentInstrumentPayload = new ProvisionedTokensForAPaymentInstrumentPayload();
        provisionedTokensForAPaymentInstrumentPayload.setApiKey(apiKey);
        provisionedTokensForAPaymentInstrumentPayload.setSharedSecret(sharedSecret);
        provisionedTokensForAPaymentInstrumentPayload.setvPanEnrollmentID(vPanEnrollmentID);
        provisionedTokensForAPaymentInstrumentPayload.setProvisionedTokensForAPaymentInstrument(provisionedTokensForAPaymentInstrument);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uriProvisionedTokensForAPaymentInstrumentEndpoint)
                .content(MAPPER.writeValueAsString(provisionedTokensForAPaymentInstrumentPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(403))
            .andExpect((MockMvcResultMatchers
                .jsonPath("$.errorResponse.message")
                .value("Visa declined this transaction. If you see this too frequently, and you think you are getting this in error please contact the issuer.")));

    }*/
}
