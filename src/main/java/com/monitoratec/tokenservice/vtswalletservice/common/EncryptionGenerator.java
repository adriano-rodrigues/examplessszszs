package com.monitoratec.tokenservice.vtswalletservice.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.util.EncryptionUtils;
import com.monitoratec.tokenservice.vtswalletservice.util.JWEUtilsUsingSharedSecret;
import com.monitoratec.tokenservice.vtswalletservice.util.JWSJUtilsUsingSharedSecret;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.monitoratec.tokenservice.vtswalletservice.util.JSONUtil.toJson;

@Component
public class EncryptionGenerator {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionGenerator.class);

    private ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private ApplicationProperties applicationProperties;

    public String encryptData(Object objectToBeEncrypted) throws JsonProcessingException, NoSuchAlgorithmException, JOSEException {
        String objectToBeEncryptedAsString = toJson(objectToBeEncrypted);
        return JWEUtilsUsingSharedSecret.createJwe(objectToBeEncryptedAsString, applicationProperties.getEncApiKey(), applicationProperties.getEncSharedSecret());
    }

    public String signEncryptedData(Object objectToBeSigned) throws JOSEException, JsonProcessingException {
        String objectToBeEncryptedAsString = MAPPER.writeValueAsString(objectToBeSigned);
        return JWSJUtilsUsingSharedSecret.createJws(objectToBeEncryptedAsString, applicationProperties.getEncSharedSecret());
    }

    public String decryptJweData(String fieldToBeDecrypted) throws NoSuchAlgorithmException, ParseException, JOSEException {
        return JWEUtilsUsingSharedSecret.decryptJwe(fieldToBeDecrypted, applicationProperties.getEncSharedSecret());
    }
}
