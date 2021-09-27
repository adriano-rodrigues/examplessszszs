package com.monitoratec.tokenservice.vtswalletservice.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.AESDecrypter;
import com.nimbusds.jose.crypto.AESEncrypter;
import com.nimbusds.jwt.EncryptedJWT;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

public final class JWEUtilsUsingSharedSecret {
    private JWEUtilsUsingSharedSecret() {
    }
    /**
     * Create JWE Using Shared Secret
     *
     * @param plainText - Plain Text to encrypt
     * @param apiKey - Key ID (API Key)
     * @param sharedSecret - Shared Secret
     * @return JWE String in Compact Serialization Format
     * @throws Exception
     */
    public static String createJwe(String plainText, String apiKey, String sharedSecret) throws
            NoSuchAlgorithmException, JOSEException {
        Date date = new Date();
        JWEHeader.Builder headerBuilder = new
                JWEHeader.Builder(JWEAlgorithm.A256GCMKW, EncryptionMethod.A256GCM);
        headerBuilder.keyID(apiKey);
        headerBuilder.customParam("iat", date.getTime() / 1000);
        JWEObject jweObject = new JWEObject(headerBuilder.build(), new
                Payload(plainText));
        jweObject.encrypt(new AESEncrypter(sha256(sharedSecret)));
        return jweObject.serialize();
    }
    /**
     * Decrypt JWE with Shared Secret
     *
     * @param jwe - JWE String in Compack Serilaization Form
     * @param sharedSecret - Shared Secret
     * @return - Plain Text
     * @throws Exception
     */
    public static String decryptJwe(String jwe, String sharedSecret) throws NoSuchAlgorithmException, ParseException, JOSEException {
        EncryptedJWT encryptedJWT = EncryptedJWT.parse(jwe);
        encryptedJWT.decrypt(new AESDecrypter(sha256(sharedSecret)));
        return encryptedJWT.getPayload().toString();
    }
    /**
     * Create A SHA256 hash of the input
     *
     * @param input - String value
     * @return - SHA-256 hash in bytes
     * @throws NoSuchAlgorithmException
     */
    private static byte[] sha256(String input) throws
            NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes(StandardCharsets.UTF_8));
        return md.digest();
    }
}