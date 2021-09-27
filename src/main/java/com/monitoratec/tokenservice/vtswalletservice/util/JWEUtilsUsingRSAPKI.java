package com.monitoratec.tokenservice.vtswalletservice.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.AESDecrypter;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

public final class  JWEUtilsUsingRSAPKI {
    /**
     * Create JWE using RSA Public Key
     *
     * @param data - Plain Text
     * @param rsaPubKey - RSA Public Key
     * @param kid - Key UserId
     * @return JWE String in compact serialization format
     * @throws Exception
     */
    public static String createJwe(String data, RSAPublicKey rsaPubKey,
                                   String kid) throws Exception {
        long currentTime = (new Date()).getTime() / 1000L;
        JWEHeader updatedHeader = (new
                JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM))
                .keyID(kid).type(JOSEObjectType.JOSE).customParam("iat",
                        currentTime).build();
        JWEObject jweObject = new JWEObject(updatedHeader, new
                Payload(data));
        RSAEncrypter encrypter = new RSAEncrypter(rsaPubKey);
        jweObject.encrypt(encrypter);
        return jweObject.serialize();
    }
    /**
     * Decrypt JWE Using RSA PKI
     *
     * @param jwe - JWE String in compact serialization format
     * @param privateKeyPem - RSA Private Key in PEM Format
     * @return Plain Text
     * @throws GeneralSecurityException
     * @throws ParseException
     */
    public static final String decryptJwe(String jwe, String privateKeyPem)
            throws GeneralSecurityException, ParseException {
        String privateKey = privateKeyPem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKey);
        String plainText;
        JWEObject jweObject = JWEObject.parse(jwe);
        JWEHeader header = jweObject.getHeader();
        JWEAlgorithm jweAlgorithm = header.getAlgorithm();
        try {
            if (JWEAlgorithm.RSA1_5.equals(jweAlgorithm) ||
                    JWEAlgorithm.RSA_OAEP_256.equals(jweAlgorithm)) {
                RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)
                        KeyFactory.getInstance("RSA").generatePrivate(new
                                PKCS8EncodedKeySpec(encodedPrivateKey));
                RSADecrypter decrypter = new RSADecrypter(rsaPrivateKey);
                jweObject.decrypt(decrypter);
                plainText = jweObject.getPayload().toString();
            } else {
                JWEDecrypter decrypterForAES = new
                        AESDecrypter(encodedPrivateKey);
                jweObject.decrypt(decrypterForAES);
                plainText = jweObject.getPayload().toString();
            }
        } catch (JOSEException e) {
            throw new GeneralSecurityException("JOSEException has encountered.", e);
        }
        return plainText;
    }
    /**
     * Load RSA Public Key From File (PEM Format)
     *
     * @param publicKeyPEM - Public Key File
     * @return {@link RSAPublicKey}
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKeyFromPemFile(String publicKeyPEM)
            throws Exception {

         publicKeyPEM = publicKeyPEM
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}
