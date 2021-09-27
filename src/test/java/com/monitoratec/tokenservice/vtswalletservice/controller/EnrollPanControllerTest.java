package com.monitoratec.tokenservice.vtswalletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.constant.device.enrollment.device.CertUsage;
import com.monitoratec.tokenservice.vtswalletservice.constant.pan.enrollment.PanSource;
import com.monitoratec.tokenservice.vtswalletservice.constant.provision.token.pan.ConsumerEntryMode;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.base.ExpirationDate;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.ChannelInfo;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.ChannelSecurityContext;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.DeviceCerts;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.device.enrollment.device.VtsCerts;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment.EnrollPan;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment.EnrollPanPayload;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.pan.enrollment.PaymentInstrument;
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
class EnrollPanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper MAPPER = new ObjectMapper();

    private URI uriEnrollPan;
    private String apiKey;
    private String sharedSecret;
    private String wrongApiKey;
    private String wrongSharedSecret;
    private String clientDeviceID;
    private String vClientID;
    private String clientAppID;
    private String clientWalletAccountID;
    private PaymentInstrument paymentInstrument;
    private ExpirationDate expirationDate;
    private ChannelInfo channelInfo;
    private ChannelSecurityContext channelSecurityContext;
    private List<VtsCerts> vtsCerts;
    private List<DeviceCerts> deviceCerts;
    private EnrollPan enrollPan;

    public EnrollPanControllerTest() throws URISyntaxException {
        uriEnrollPan = new URI("/panEnrollment");
        apiKey = "D740AEMAPUJOUQCEOR8O21qji0tMVF7qteDkuiea-Mad882is";
        sharedSecret = "nY8rzX-9Sq09WCUq23-JXqNdyhLWXxPA@LvFulK@";
        wrongApiKey = apiKey + "1";
        wrongSharedSecret = sharedSecret + "1";
        clientDeviceID = "1374184250012220225";
        vClientID = "ddf54e2c-6b6f-bfd8-6645-17d241fc9a01";
        clientAppID = "firstTech";
        clientWalletAccountID = "AiOxOitsfavni990I6";
        expirationDate = ExpirationDate.builder().year("2022").month("12").build();
        paymentInstrument = PaymentInstrument.builder().accountNumber("4622943127025824").cvv2("651").expirationDate(expirationDate).build();
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setEncryptionScheme("RSA_PKI");

        vtsCerts = new ArrayList<>();
        vtsCerts.add(VtsCerts.builder()
            .vCertificateID("27ffe2c7")
            .certUsage(CertUsage.CONFIDENTIALITY)
            .build());
        vtsCerts.add(VtsCerts.builder()
            .vCertificateID("715ea257")
            .certUsage(CertUsage.INTEGRITY)
            .build());

        deviceCerts = new ArrayList<>();
        deviceCerts.add(DeviceCerts.builder()
            .certFormat("X509")
            .certUsage(CertUsage.CONFIDENTIALITY)
            .certValue("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUQzVENDQXNVQ0ZESjY3TEdzeU5SMzJRTWNwcVF2RFM1MUNnZ1dNQTBHQ1NxR1NJYjNEUUVCQ3dVQU1JR2wKTVFzd0NRWURWUVFHRXdKQ1VqRVNNQkFHQTFVRUNBd0pVMkZ2SUZCaGRXeHZNUkl3RUFZRFZRUUhEQWxUWVc4ZwpVR0YxYkc4eEV6QVJCZ05WQkFvTUNrWnBjbk4wSUZSbFkyZ3hFVEFQQmdOVkJBc01DRlpwYzJFZ1ZsUlRNUmt3CkZ3WURWUVFEREJBcUxtWnBjbk4wTFhSbFkyZ3VibVYwTVNzd0tRWUpLb1pJaHZjTkFRa0JGaHhxYjJGdkxtOXMKYVhabGFYSmhRR1pwY25OMExYUmxZMmd1Ym1WME1CNFhEVEl4TURrd09ERXpORGd3TWxvWERUSXpNREV5TVRFegpORGd3TWxvd2dhOHhDekFKQmdOVkJBWVRBa0pTTVJJd0VBWURWUVFJREFsVFlXOGdVR0YxYkc4eEV6QVJCZ05WCkJBY01DbE5oYnlCRFlYSnNiM014SnpBbEJnTlZCQW9NSGsxdmJtbDBiM0poSUZOdmJIVmpiMlZ6SUZSbFkyNXYKYkc5bmFXTmhjekVaTUJjR0ExVUVDd3dRUkdsbmFYUmhiQ0JUWlhKMmFXTmxjekVWTUJNR0ExVUVBd3dNVFc5aQphV3hsUkdWMmFXTmxNUnd3R2dZSktvWklodmNOQVFrQkZnMWxiV0ZwYkVCdmNtY3VZMjl0TUlJQklqQU5CZ2txCmhraUc5dzBCQVFFRkFBT0NBUThBTUlJQkNnS0NBUUVBdDhoVVN1TUVLNm1zQXdQN2N6dE93a2NKTmVpeVdkWVgKWnRJL2F2WDM4dk0vSjlwUGg3TGw4Vi9LVFQ1QkphNTA4YjBtc0x5UnlLQkVuYTYrRmFLZEl2QVRmYjBOaHFaeApycGRGUDRZcElNRnhTbnZPWnhIVzJMNmV4WjdObFFsc1E5dGc5SUJhNUUrTEgrdS9ueFZEbGZoMjBNMEc0emJICjNvSlltNmtYbTcxcUluNjMzcjIwazZzbFZGM3ZBOFVVTk43Z3ArRVFDSW5SemdqNG42UzNqME1yZEFlOVAxZWYKcnBMa1FFenBvaG5LN05yWHQ5alc0T2k0cFZvYkRzd2djSjdyaXQwNnRFQUdNL0djako0K0s1V3l0aVFNSnlobApRZWlOemk5QzZiUXJqQzdYNEk1a29QMXFqTUFnUjlJaDllNW5wMHkwVk1SYUJmMVlncXNjSlFJREFRQUJNQTBHCkNTcUdTSWIzRFFFQkN3VUFBNElCQVFDbUU2VnJFL281b1F0YWRxeU11SktwaWFjNFZ6L1VSSTdrRERQUW1wbDAKb2lpQmhybVl6Tk1vQ2FvaHMzSDQ4ai9DMktkU2hLbFg4R3hDWDdrN2VhVUVDZjdkanM2blRlZ2d0WnNoS0c1NQpjR0hGU0FaMnA4elNTWEN4MXpWWm5aZDZBbk1mQ05kRWNtTmowYXZHZHdTRTBDQldvMWl3UkpadlpOdWJPUG9QCk0zVGsyR3JDcGV4S3ZwWUhTWTRTcSs4QkgyY1F5cjFING95Qm5pRXd4VWJMVXJlS04yWFBUcEt1UXNtcGJVRUkKVnNkZ3JnUGg0bTEycDk3WmJoQ3N0aWR1WCtERHB6QnFGdlpnWUF1QVlRbVRrbVNvMnI5WjE2V2ZIRUplZ3JCNQp2MjMyRW5pRUo2SzJNL25zV0M4eWRHV3hXc0tBSndUb1RPT1JWQ1VtRWNzZwotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg")
            .build());
        deviceCerts.add(DeviceCerts.builder()
            .certFormat("X509")
            .certUsage(CertUsage.INTEGRITY)
            .certValue("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUQzVENDQXNVQ0ZESjY3TEdzeU5SMzJRTWNwcVF2RFM1MUNnZ1hNQTBHQ1NxR1NJYjNEUUVCQ3dVQU1JR2wKTVFzd0NRWURWUVFHRXdKQ1VqRVNNQkFHQTFVRUNBd0pVMkZ2SUZCaGRXeHZNUkl3RUFZRFZRUUhEQWxUWVc4ZwpVR0YxYkc4eEV6QVJCZ05WQkFvTUNrWnBjbk4wSUZSbFkyZ3hFVEFQQmdOVkJBc01DRlpwYzJFZ1ZsUlRNUmt3CkZ3WURWUVFEREJBcUxtWnBjbk4wTFhSbFkyZ3VibVYwTVNzd0tRWUpLb1pJaHZjTkFRa0JGaHhxYjJGdkxtOXMKYVhabGFYSmhRR1pwY25OMExYUmxZMmd1Ym1WME1CNFhEVEl4TURrd09ERXpOVEF4T1ZvWERUSXpNREV5TVRFegpOVEF4T1Zvd2dhOHhDekFKQmdOVkJBWVRBa0pTTVJJd0VBWURWUVFJREFsVFlXOGdVR0YxYkc4eEV6QVJCZ05WCkJBY01DbE5oYnlCRFlYSnNiM014SnpBbEJnTlZCQW9NSGsxdmJtbDBiM0poSUZOdmJIVmpiMlZ6SUZSbFkyNXYKYkc5bmFXTmhjekVaTUJjR0ExVUVDd3dRUkdsbmFYUmhiQ0JUWlhKMmFXTmxjekVWTUJNR0ExVUVBd3dNVFc5aQphV3hsUkdWMmFXTmxNUnd3R2dZSktvWklodmNOQVFrQkZnMWxiV0ZwYkVCdmNtY3VZMjl0TUlJQklqQU5CZ2txCmhraUc5dzBCQVFFRkFBT0NBUThBTUlJQkNnS0NBUUVBeGc2U0hzVnJYaHVoS0dTRk5ONHhZYWREWHhQWHdUSjYKNE02TlZma2xEOVh0bE5hZmQrV25iK0ZndHNQSGljYXVKZVA0cXRqQmNEZmkvRGQrRU9pSk1PT21ham5PTzNGOAowOEtWZ2hFM0lhekU5Mnp6OGplWmVqcVlEanMxZVJhclIvdmJhL1hKbFdXOGhBVkQ2dzYwSHppcm9YRStFZ3EwCkFDRS9DTE8xbUQycXhaak42dUlwR0dwNWsxK2Y5SlpjdE4wSkE0MEI5WFR4VEplb05DdGNVOWZpQ0ZWZXR0KzcKdVVrSndYMFB6MFRQK0FSbWVITDhFNTZvRlJRUEhBZ2Mva0JkWWt0ZkpWSzdTWnFvZ1NXdlM1TFhWc0kvUmoxYQpNaWJmbnFvWGkzWjhDeXlSdDRSSjZQdFRNcThVa1JLbXZYSXpQRTUzaW9RREVVUWhXZWc5alFJREFRQUJNQTBHCkNTcUdTSWIzRFFFQkN3VUFBNElCQVFDUEZuMXdTTlp5UTRJeGljSEEvb1VMUTlYUmlPc1c1bXNGQ0lwaThTMnEKZ3VFVWhsZm9nTXQyYnMzdDk2VCs1ZU8wNHhFSG0rMm5qcVd0MGN2N3NvVUM4em82eHVFZitzdzNVdjI2eExhbwp1cXFWOVhuYlhCM1hvdFRPcXJJdDNpa1FjR1pVQi8rRkhsTDVJUVc2bGk1alNTQjhyUlI3T0tuN0lUNG5sLzVTCnl1RmtpQnF5TEp2cWRwTFphS3F1UkNKbUV2WkVQWHIyNmZIdnp2ZVNKbmJIc1NTY0VOeGlXRUU2SkNXL3JRUmoKYnFsWm5aKzhVOFhuVXFOS1BpSjNBeFJ1bkdPRWk5c1ROK01MT1c0NTdBSnhkRjVmMWN4a0NXL21MRFozQmloRwpPWlM0UTlVTTF6L0l3R2I1c1NPandHNUNSUjNjTW1WVmc1Vm1tU29TT2FrcQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg")
            .build());

        channelSecurityContext = ChannelSecurityContext.builder()
            .channelInfo(channelInfo)
            .deviceCerts(deviceCerts)
            .vtsCerts(vtsCerts)
            .build();

        enrollPan = EnrollPan.builder()
            .channelSecurityContext(channelSecurityContext)
            .clientAppID(clientAppID)
            .clientDeviceID(clientDeviceID)
            .clientWalletAccountID(clientWalletAccountID)
            .locale("en_US")
            .panSource(PanSource.MANUALLYENTERED)
            .consumerEntryMode(ConsumerEntryMode.KEYENTERED)
            .paymentInstrument(paymentInstrument)
            .build();
    }

    @DisplayName("Check endpoint response")
    @Test
    public void WhenSendRequestToEnrollPanEndpointItResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(uriEnrollPan))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @DisplayName("Check dont auth in VTS")
    @Test
    public void WhenApiKeyAndSharedSecretAreWrongNoAuthInVTSWithEnrollPanEndpoint() throws Exception {
         EnrollPanPayload enrollPanPayload = EnrollPanPayload.builder()
            .apiKey(wrongApiKey)
            .sharedSecret(wrongSharedSecret)
            .enrollPan(enrollPan)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uriEnrollPan)
                .content(MAPPER.writeValueAsString(enrollPanPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(401)
            );
    }

   /* @Ignore
    @DisplayName("Check Enroll PAN process")
    @Test
    public void WhenSendAPANWasEnrolledToEnrollPanEndpointItReturnsAlert() throws Exception {
        EnrollPanPayload enrollPanPayload = EnrollPanPayload.builder()
            .apiKey(apiKey)
            .sharedSecret(sharedSecret)
            .enrollPan(enrollPan)
            .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uriEnrollPan)
                .content(MAPPER.writeValueAsString(enrollPanPayload))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(403))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.errorResponse.message")
                .value("Visa declined this transaction. If you see this too frequently, and you think you are getting this in error please contact the issuer."));
    }*/

}
