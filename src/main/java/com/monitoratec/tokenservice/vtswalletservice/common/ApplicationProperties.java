package com.monitoratec.tokenservice.vtswalletservice.common;

import com.monitoratec.tokenservice.vtswalletservice.util.EncryptionUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public @Data class ApplicationProperties {

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${vts.application.cte.apiKeyQueryParam}")
    private String apiKeyQueryParam;
    @Value("${vts.application.getPanDataPath}")
    private String panDataPath;
    @Value("${vts.application.deviceEnrollmentPath}")
    private String deviceEnrollmentPath;
    @Value("${vts.application.enrollPan}")
    private String enrollPanPath;
    @Value("${vts.application.confirmProvisioningPath}")
    private String confirmProvisioningPath;
    @Value("${vts.application.provisionedTokensForAPanPath}")
    private String provisionedTokensForAPanPath;
    @Value("${vts.application.provisionedTokensForAPaymentInstrumentPath}")
    private String provisionedTokensForAPaymentInstrument;
    @Value("${vts.application.provisionedTokensForAEnrolledDevice}")
    private String provisionedTokensForAEnrolledDevice;
    @Value("${vts.application.provisionedTokensForDeleteDeviceTokenBinding}")
    private String provisionedTokensForDeleteDeviceTokenBinding;
    @Value("${vts.application.dasDeviceEnrollmentPath}")
    private String dasDeviceEnrollmentPath;
    @Value("${vts.encryptionApiKey}")
    private String encApiKey;
    @Value("${vts.encryptionSharedSecret}")
    private String encSharedSecret;

    public String getVisaSignedDeviceSignConfidentialityPem() throws IOException {
        Resource resource=resourceLoader.getResource("classpath:visa-signed-certs/device_sign_confidentiality.pem");
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String base64encodedResponse=EncryptionUtils.base64encode(FileCopyUtils.copyToString(reader));
        return base64encodedResponse;
    }
    public String getVisaSignedDeviceSignIntegrityCrt() throws IOException {
        Resource resource=resourceLoader.getResource("classpath:visa-signed-certs/device_sign_integrity.crt");
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String base64encodedResponse=EncryptionUtils.base64encode(FileCopyUtils.copyToString(reader));
        return base64encodedResponse;
    }

    public String getFirstTechDeviceCertsSignedVdpcaPem() throws IOException {
        Resource resource=resourceLoader.getResource("classpath:first-tech-device-certs-signed/VDPCA.pem");
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String base64encodedResponse=EncryptionUtils.base64encode(FileCopyUtils.copyToString(reader));
        return base64encodedResponse.replace("=","");
    }

    public String getFirstTechDeviceCertsSignedConfidentialityPem() throws IOException {
        Resource resource=resourceLoader.getResource("classpath:first-tech-device-certs-signed/device_sign_confidentiality.pem");
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String base64encodedResponse=EncryptionUtils.base64encode(FileCopyUtils.copyToString(reader));
        return base64encodedResponse.replace("=","");
    }
    public String getFirstTechDeviceCertsSignedConfidentialityCrt() throws IOException {
        Resource resource=resourceLoader.getResource("classpath:first-tech-device-certs-signed/device_sign_integrity.crt");
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String base64encodedResponse=EncryptionUtils.base64encode(FileCopyUtils.copyToString(reader));
        return base64encodedResponse.replace("=","");
    }
}
