package com.monitoratec.tokenservice.vtswalletservice.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JWSJUtilsUsingSharedSecret {

    /**
     * Create JWS Using Shared Secret
     *
     * @param jwe - JWE String
     * @param sharedSecret - Shared Secret
     * @return JWS in Compact Serialization format
     * @throws Exception
     */
    public static String createJws(String jwe, String sharedSecret) throws JOSEException {
        Map<String, Object> customParameters = new HashMap<>();
        Date date = new Date();
        long iat = date.getTime() / 1000;
        Long exp = iat + 120;
        customParameters.put("iat", iat);
        customParameters.put("exp", exp);
        JWSHeader.Builder builder = new JWSHeader.Builder(JWSAlgorithm.HS256);
        builder.customParams(customParameters);
        JWSHeader jwsHeader = builder.build();
        JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(jwe));
        jwsObject.sign(new
                MACSigner(sharedSecret.getBytes(StandardCharsets.UTF_8)));
        return jwsObject.serialize();
    }
    /**
     * Verify And Extract JWE from JWS
     *
     * @param jws - JWS in Compact Serialization format
     * @param sharedSecret - Shared Secret
     * @return JWE String
     * @throws Exception
     */
    public static String verifyAndExtractJweFromJWS(String jws, String
            sharedSecret) throws Exception {
        JWSObject jwsObject = JWSObject.parse(jws);
        if (!jwsObject.verify(new
                MACVerifier(sharedSecret.getBytes(StandardCharsets.UTF_8)))) {
            throw new Exception("Invalid signature");
        }
        Map<String, Object> customParameters =
                jwsObject.getHeader().getCustomParams();
        if (customParameters == null) {
            throw new Exception("Invalid signature");
        }
        Long now = System.currentTimeMillis() / 1000;
        if ((Long) customParameters.get("iat") > now || (Long)
                customParameters.get("exp") < now) {
            throw new Exception("Invalid signature");
        }
        return jwsObject.getPayload().toString();
    }
}
