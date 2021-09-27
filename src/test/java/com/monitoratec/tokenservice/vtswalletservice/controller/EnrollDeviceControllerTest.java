package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.constant.DeviceType;
import com.monitoratec.tokenservice.vtswalletservice.constant.OsType;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.device.CertUsage;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.das.DasDeviceEnrollmentPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.delete.DeleteDeviceTokenBindingPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.*;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class EnrollDeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper MAPPER = new ObjectMapper();

    private URI uriDeleteDeviceTokenBinding;
    private URI uriDasDeviceEnrollment;
    private URI uriEnrollDevice;
    private String apiKey;
    private String sharedSecret;
    private String wrongApiKey;
    private String wrongSharedSecret;
    private String vProvisionedTokenID;
    private String clientDeviceID;
    private String vClientID;
    private EnrollDevice enrollDevice;
    private List<VtsCerts> vtsCerts;
    private ChannelInfo channelInfo;
    private List<DeviceCerts> deviceCerts;
    private ChannelSecurityContext channelSecurityContext;

    public EnrollDeviceControllerTest() throws URISyntaxException {
        uriDeleteDeviceTokenBinding = new URI("/deviceEnrollment/provisionedTokens");
        uriDasDeviceEnrollment = new URI("/deviceEnrollment/DasDeviceEnrollment");
        uriEnrollDevice = new URI("/deviceEnrollment");
        apiKey = "D740AEMAPUJOUQCEOR8O21qji0tMVF7qteDkuiea-Mad882is";
        sharedSecret = "nY8rzX-9Sq09WCUq23-JXqNdyhLWXxPA@LvFulK@";
        wrongApiKey = apiKey + "1";
        wrongSharedSecret = sharedSecret + "1";
        vProvisionedTokenID = "adc24340ad9511e4bec7e2fd83f838a9";
        clientDeviceID = "1374184250012220225";
        vClientID = "ddf54e2c-6b6f-bfd8-6645-17d241fc9a01";

        vtsCerts = new ArrayList<>();
        vtsCerts.add(VtsCerts.builder().certUsage(CertUsage.CONFIDENTIALITY).vCertificateID("27ffe2c7").build());
        vtsCerts.add(VtsCerts.builder().certUsage(CertUsage.INTEGRITY).vCertificateID("715ea257").build());

        channelInfo = new ChannelInfo();
        channelInfo.setEncryptionScheme("RSA_PKI");

        deviceCerts = new ArrayList<>();
        deviceCerts.add(DeviceCerts.builder().certUsage(CertUsage.CONFIDENTIALITY).certFormat("X509").certValue("YourownconfidentialityCert").build());
        deviceCerts.add(DeviceCerts.builder().certUsage(CertUsage.INTEGRITY).certFormat("X509").certValue("YourownintegrityCert").build());

        channelSecurityContext = ChannelSecurityContext.builder()
            .vtsCerts(vtsCerts)
            .channelInfo(channelInfo)
            .deviceCerts(deviceCerts)
            .build();

        enrollDevice = EnrollDevice.builder()
            .deviceInfo(DeviceInfo.builder()
                .osType(OsType.ANDROID)
                .osVersion("4.4.4")
                .osBuildID("KTU84M")
                .deviceType(DeviceType.PHONE)
                .deviceIDType("TEE")
                .deviceManufacturer("Samsung")
                .deviceBrand("Android Brand")
                .deviceModel("ANDROID-999")
                .deviceName("MyWatch")
                .hostDeviceID("3e89278f175403c4fc4e0621")
                .phoneNumber("7862011714")
                .build())
            .channelSecurityContext(channelSecurityContext)
            .build();
    }

    @DisplayName("Check Delete Device Token Binding endpoint response")
    @Test
    public void WhenSendRequestToDeleteDeviceTokenBindingEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .delete(uriDeleteDeviceTokenBinding))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check Delete Device Token Binding endpoint auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreCorrectAuthInVTSWithDeleteDeviceTokenBindingEndpoint() throws Exception {
        DeleteDeviceTokenBindingPayload deleteDeviceTokenBindingPayload = new DeleteDeviceTokenBindingPayload();
        deleteDeviceTokenBindingPayload.setApiKey(apiKey);
        deleteDeviceTokenBindingPayload.setSharedSecret(sharedSecret);
        deleteDeviceTokenBindingPayload.setvProvisionedTokenID(vProvisionedTokenID);
        deleteDeviceTokenBindingPayload.setClientDeviceID(clientDeviceID);

        mockMvc.perform(MockMvcRequestBuilders
            .delete(uriDeleteDeviceTokenBinding)
            .content(MAPPER.writeValueAsString(deleteDeviceTokenBindingPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400)
            )
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.errorResponse.message")
                .value("vProvisionedTokenId does not correspond to a valid token")
            );
    }

    @DisplayName("Check Delete Device Token Binding endpoint dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTSWithDeleteDeviceTokenBindingEndpoint() throws Exception {
        DeleteDeviceTokenBindingPayload deleteDeviceTokenBindingPayload = new DeleteDeviceTokenBindingPayload();
        deleteDeviceTokenBindingPayload.setApiKey(wrongApiKey);
        deleteDeviceTokenBindingPayload.setSharedSecret(wrongSharedSecret);
        deleteDeviceTokenBindingPayload.setvProvisionedTokenID(vProvisionedTokenID);
        deleteDeviceTokenBindingPayload.setClientDeviceID(clientDeviceID);

        mockMvc.perform(MockMvcRequestBuilders
            .delete(uriDeleteDeviceTokenBinding)
            .content(MAPPER.writeValueAsString(deleteDeviceTokenBindingPayload))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401));
    }

    @DisplayName("Check Das Device Enrollment endpoint response")
    @Test
    public void WhenSendRequestToDasDeviceEnrollmentEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(uriDasDeviceEnrollment))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check Das Device Enrollment endpoint auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreCorrectAuthInVTSWithDasDeviceEnrollmentEndpoint() throws Exception {
        DasDeviceEnrollmentPayload dasDeviceEnrollmentPayload = DasDeviceEnrollmentPayload.builder()
            .apiKey(apiKey)
            .sharedSecret(sharedSecret)
            .vClientID(vClientID)
            .clientDeviceID(clientDeviceID)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uriDasDeviceEnrollment)
                .content(MAPPER.writeValueAsString(dasDeviceEnrollmentPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400)
            )
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.responseStatus.message")
                .value("Expected input credential was not present")
            );
    }

    @DisplayName("Check Enroll Device endpoint dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTSWithEnrollDeviceEndpoint() throws Exception{
        EnrollDevicePayload enrollDevicePayload = EnrollDevicePayload.builder()
            .apiKey(wrongApiKey)
            .sharedSecret(wrongSharedSecret)
            .vClientId(vClientID)
            .enrollDevice(enrollDevice)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .put(uriEnrollDevice)
                .content(MAPPER.writeValueAsString(enrollDevicePayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401));

    }

    @DisplayName("Check Enroll Device process")
    @Test
    public void WhenSendEnrollDeviceEndpointItReturnsOk() throws Exception{
        EnrollDevicePayload enrollDevicePayload = EnrollDevicePayload.builder()
            .apiKey(apiKey)
            .sharedSecret(sharedSecret)
            .vClientId(vClientID)
            .enrollDevice(enrollDevice)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .put(uriEnrollDevice)
                .content(MAPPER.writeValueAsString(enrollDevicePayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(200))
            .andExpect(MockMvcResultMatchers.
                jsonPath("$.vClientID").
                value(vClientID)
            );

    }
}
