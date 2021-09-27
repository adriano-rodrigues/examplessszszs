package com.monitoratec.tokenservice.vtswalletservice.util;

import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.EncryptedJWT;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class EncryptionUtils {

    private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private static final String CONTENT_TYPE_JWE = "JWE";
    private static final String CONTENT_TYPE_XML = "application/json";
    private static final String SHA_256 = "SHA-256";
    private static final String ERROR_MESSAGE_INVALID_SIGNATURE = "Invalid signature";
    private static final String HEADER_CTY = "cty";
    private static final String HEADER_IAT = "iat";
    private static final String HEADER_EXP = "exp";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private EncryptionUtils() {
    }


    /**
     * Create JWE Using API Key & Shared Secret Key
     *
     * @param plainText         - The Plain text, unencrypted data
     * @param apiKey            - API Key
     * @param sharedSecret      - Shared Secret
     * @param jweAlgorithm      - The JWE Encryption Algorithm
     * @param encryptionMethod  - The JWE Encryption Method
     * @param additionalHeaders - Additional JWE Headers
     * @return - JWE String in compact serialization format
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String createJwe(String plainText, String apiKey, String sharedSecret, JWEAlgorithm jweAlgorithm,
                                   EncryptionMethod encryptionMethod, Map<String, Object> additionalHeaders) throws GenericSecurityException {
        JWEHeader jweHeader = header(apiKey, jweAlgorithm, encryptionMethod, additionalHeaders);
        JWEObject jweObject = new JWEObject(jweHeader, new Payload(plainText));
        try {
            jweObject.encrypt(new AESEncrypter(sha256(sharedSecret)));
            return jweObject.serialize();
        } catch (JOSEException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        }
    }

    public static String base64decode(String base64encoded){
        byte[] base64EncodeOutput = java.util.Base64.getDecoder().decode( base64encoded );
        String base64DecodedStr = Strings.fromUTF8ByteArray( base64EncodeOutput );
        return base64DecodedStr;
    }

    public static String  base64encode(String toBeEncoded){
        return Base64.getEncoder().encodeToString(toBeEncoded.getBytes());
    }

    /**
     * Create JWE Using API Key & Shared Secret Key for XML Payload
     *
     * @param plainText         - The Plain text, unencrypted data (in XML format)
     * @param apiKey            - API Key
     * @param sharedSecret      - Shared Secret
     * @param jweAlgorithm      - The JWE Encryption Algorithm
     * @param encryptionMethod  - The JWE Encryption Method
     * @param additionalHeaders - Additional JWE Headers
     * @return - JWE String in compact serialization format
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String createJweWithXmlPayload(String plainText, String apiKey, String sharedSecret, JWEAlgorithm jweAlgorithm,
                                                 EncryptionMethod encryptionMethod, Map<String, Object> additionalHeaders) throws GenericSecurityException {
        if (additionalHeaders == null) {
            additionalHeaders = new HashMap<String, Object>();
        }
        additionalHeaders.put(HEADER_CTY, CONTENT_TYPE_XML);
        return createJwe(plainText, apiKey, sharedSecret, jweAlgorithm, encryptionMethod, additionalHeaders);
    }

    /**
     * Decrypt JWE with Shared Secret
     *
     * @param jweString    - JWE String in Compact Serialization format
     * @param sharedSecret - Shared Secret
     * @return - Plain Text
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String decryptJwe(String jweString, String sharedSecret) throws GenericSecurityException {
        try {
            EncryptedJWT encryptedJWT = EncryptedJWT.parse(jweString);
            encryptedJWT.decrypt(new AESDecrypter(sha256(sharedSecret)));
            return encryptedJWT.getPayload().toString();
        } catch (Exception e) {
            throw new GenericSecurityException(e.getMessage(), e);
        }
    }

    /**
     * Create JWS Using Shared Secret
     *
     * @param jweString         - JWE String
     * @param sharedSecret      - Shared Secret
     * @param additionalHeaders - Additional JWE Headers
     * @return JWS in Compact Serialization format
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String createJws(String jweString, String sharedSecret, Map<String, Object> additionalHeaders) throws GenericSecurityException {
        try {
            JWSObject jwsObject = new JWSObject((new JWSHeader.Builder(JWSAlgorithm.HS256))
                    .type(JOSEObjectType.JOSE).contentType(CONTENT_TYPE_JWE).customParams(additionalHeaders).build(), new Payload(jweString));
            jwsObject.sign(new MACSigner(sharedSecret.getBytes(CHARSET_UTF_8)));
            return jwsObject.serialize();
        } catch (KeyLengthException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        } catch (JOSEException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        }
    }

    /**
     * Verify And Extract JWE from JWS Using Shared Secret
     *
     * @param jws          - JWS in Compact Serialization format
     * @param sharedSecret - Shared Secret
     * @return JWE String
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String verifyAndExtractJweFromJWS(String jws, String sharedSecret) throws Exception {
        JWSObject jwsObject = JWSObject.parse(jws);
        if (!jwsObject.verify(new MACVerifier(sharedSecret.getBytes(CHARSET_UTF_8)))) {
            throw new GenericSecurityException(ERROR_MESSAGE_INVALID_SIGNATURE);
        }
        Map<String, Object> customParameters = jwsObject.getHeader().getCustomParams();
        Long now = System.currentTimeMillis() / 1000;
        if (customParameters != null && customParameters.get(HEADER_IAT) != null
                && ((Long) customParameters.get(HEADER_IAT) > now || (Long) customParameters.get(HEADER_EXP) < now)) {
            throw new GenericSecurityException(ERROR_MESSAGE_INVALID_SIGNATURE);
        }
        return jwsObject.getPayload().toString();
    }


    /**
     * Create JWE using RSA PKI
     *
     * @param plainText         - The Plain text, unencrypted data
     * @param kid               - Key User ID
     * @param rsaPubKey         - The RSA PublicKey
     * @param jweAlgorithm      - The JWE Encryption Algorithm
     * @param encryptionMethod  - The JWE Encryption Method
     * @param additionalHeaders - Additional JWE Headers
     * @return - JWE String in compact serialization format
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String createJwe(String plainText, String kid, RSAPublicKey rsaPubKey, JWEAlgorithm jweAlgorithm,
                                   EncryptionMethod encryptionMethod, Map<String, Object> additionalHeaders) throws GenericSecurityException {
        JWEHeader jweHeader = header(kid, jweAlgorithm, encryptionMethod, additionalHeaders);
        JWEObject jweObject = new JWEObject(jweHeader, new Payload(plainText));
        try {
            jweObject.encrypt(new RSAEncrypter(rsaPubKey));
            return jweObject.serialize();
        } catch (JOSEException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        }
    }

    /**
     * Decrypt JWE Using RSA PKI
     *
     * @param jweString     - JWE String in compact serialization format
     * @param rsaPrivateKey - RSA Private Key
     * @return Plain Text
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String decryptJwe(String jweString, RSAPrivateKey rsaPrivateKey) throws GenericSecurityException {
        try {
            JWEObject jweObject = JWEObject.parse(jweString);
            JWEHeader header = jweObject.getHeader();
            JWEAlgorithm jweAlgorithm = header.getAlgorithm();
            if (JWEAlgorithm.RSA_OAEP_256.equals(jweAlgorithm)) {
                jweObject.decrypt(new RSADecrypter(rsaPrivateKey));
                return jweObject.getPayload().toString();
            } else {
                throw new GenericSecurityException("Unsupported JWE Algorithm: " + jweAlgorithm);
            }
        } catch (JOSEException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new GenericSecurityException("Invalid JWE String: " + jweString);
        }
    }

    /**
     * Create JWS Using RSA PKI
     *
     * @param jwe               - JWE Payload
     * @param signingKid        - Signing Key UserID
     * @param rsaPrivateKey     - RSA Private Key
     * @param additionalHeaders - Additional JWE Headers
     * @return JWS in Compact Serialization format
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String createJws(String jwe, String signingKid, RSAPrivateKey rsaPrivateKey, Map<String, Object> additionalHeaders) throws GenericSecurityException {
        JWSObject jwsObject = new JWSObject((new JWSHeader.Builder(JWSAlgorithm.PS256))
                .type(JOSEObjectType.JOSE).keyID(signingKid).contentType(CONTENT_TYPE_JWE).customParams(additionalHeaders).build(), new Payload(jwe));
        JWSSigner signer = new RSASSASigner(rsaPrivateKey);
        try {
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        }
    }

    /**
     * Verify And Extract JWE from JWS Using Shared Secret
     *
     * @param jws       - JWS Compact Serialization Format
     * @param publicKey - Public Key
     * @return JWE String
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    public static String verifyAndExtractJweFromJWS(String jws, RSAPublicKey publicKey) throws GenericSecurityException {
        try {
            JWSObject jwsObject = JWSObject.parse(jws);
            if (!jwsObject.verify(new RSASSAVerifier(publicKey))) {
                throw new GenericSecurityException(ERROR_MESSAGE_INVALID_SIGNATURE);
            }
            return jwsObject.getPayload().toString();
        } catch (ParseException e) {
            throw new GenericSecurityException("Invalid JWS String. " + e.getMessage(), e);
        } catch (JOSEException e) {
            throw new GenericSecurityException(e.getMessage(), e);
        }
    }


    /**
     * Construct the JWE Header
     *
     * @param kid               -The Key User ID
     * @param jweAlgorithm      - The JWE Encryption Algorithm
     * @param encryptionMethod  - The JWE Encryption Method
     * @param additionalHeaders - Additional JWE Headers
     * @return {@link JWEHeader}
     */
    private static JWEHeader header(String kid, JWEAlgorithm jweAlgorithm, EncryptionMethod encryptionMethod, Map<String, Object> additionalHeaders) {
        JWEHeader.Builder builder = new JWEHeader.Builder(jweAlgorithm, encryptionMethod).keyID(kid).type(JOSEObjectType.JOSE);
        if (additionalHeaders != null && additionalHeaders.size() > 0) {
            for (String k : additionalHeaders.keySet()) {
                Object value = additionalHeaders.get(k);
                if (HEADER_CTY.equalsIgnoreCase(k)) {
                    builder.contentType(value.toString());
                } else {
                    builder.customParam(k, additionalHeaders.get(k));
                }
            }
        }
        return builder.build();
    }

    /**
     * Create A SHA256 hash of the input
     *
     * @param input - String value
     * @return - SHA-256 hash in bytes
     * @throws GenericSecurityException - {@link GenericSecurityException}
     */
    private static byte[] sha256(String input) throws GenericSecurityException {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(input.getBytes(CHARSET_UTF_8));
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new GenericSecurityException("No Such Algorithm", e);
        }
    }
}