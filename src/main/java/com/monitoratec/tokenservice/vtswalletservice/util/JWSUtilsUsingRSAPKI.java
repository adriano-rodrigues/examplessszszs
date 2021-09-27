package com.monitoratec.tokenservice.vtswalletservice.util;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
public class JWSUtilsUsingRSAPKI {
    /**
     * Create JWS Using RSA PKI
     *
     * @param jwe - JWE Payload
     * @param rsaPrivateKey - RSA Private Key
     * @param signKid - Signing Key UserID
     * @return JWS Compact Serialization Format
     * @throws JOSEException
     */
    public static String createJws(String jwe, PrivateKey rsaPrivateKey,
                                   String signKid) throws JOSEException {
        JWSObject jwsObject = new JWSObject((new
                com.nimbusds.jose.JWSHeader.Builder(JWSAlgorithm.PS256))

                .type(JOSEObjectType.JOSE).keyID(signKid).contentType("JWE").build(), new
                Payload(jwe));
        JWSSigner signer = new RSASSASigner(rsaPrivateKey);
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }
    /**
     * Verify A JWS
     *
     * @param jws - JWS Compact Serialization Format
     * @param publicKey - Public Key
     * @return
     */
    public static boolean verifyJws(String jws, byte[] publicKey) {
        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey)
                    KeyFactory.getInstance("RSA").generatePublic(new
                            X509EncodedKeySpec(publicKey));
            JWSVerifier verifier = new RSASSAVerifier(rsaPublicKey);
            JWSObject jwsObject = JWSObject.parse(jws);
            return jwsObject.verify(verifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static RSAPrivateKey readPrivateKey(String privateKeyPEM) throws Exception {

        privateKeyPEM = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}