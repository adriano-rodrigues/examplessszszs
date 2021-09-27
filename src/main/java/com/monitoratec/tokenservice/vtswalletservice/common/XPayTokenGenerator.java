package com.monitoratec.tokenservice.vtswalletservice.common;

import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;


@Component
public class XPayTokenGenerator {

    private static final Logger logger = LoggerFactory.getLogger(XPayTokenGenerator.class);

    /**
     *
     * @param resourcePath
     * @param queryString
     * @param requestBody
     * @param sharedSecret
     * @return
     * @throws SignatureException
     */
    public  String get(String resourcePath, String queryString, String requestBody, String sharedSecret) throws XPayTokenException {
        logger.info("Generating x-pay-token");
        String timestamp = timeStamp();
        String beforeHash = timestamp.concat(resourcePath).concat(queryString).concat(requestBody);
        String hash = hmacSha256Digest(beforeHash, sharedSecret);
        return "xv2:".concat(timestamp).concat(":").concat(hash);
    }

    /**
     *
     * @return
     */
    private  String timeStamp() {
        return String.valueOf(System.currentTimeMillis()/ 1000L);
    }

    /**
     *
     * @param data
     * @param sharedSecret
     * @return
     * @throws XPayTokenException
     */
    private  String hmacSha256Digest(String data, String sharedSecret)
            throws XPayTokenException {
        return getDigest("HmacSHA256", sharedSecret, data, true);
    }

    /**
     *
     * @param algorithm
     * @param sharedSecret
     * @param data
     * @param toLower
     * @return
     * @throws SignatureException
     */
    private   String getDigest(String algorithm, String sharedSecret, String data,  boolean toLower) throws XPayTokenException {
        try {
            Mac sha256HMAC = Mac.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(sharedSecret.getBytes(StandardCharsets.UTF_8), algorithm);
            sha256HMAC.init(secretKey);

            byte[] hashByte = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String hashString = toHex(hashByte);

            return toLower ? hashString.toLowerCase() : hashString;
        } catch (Exception e) {
            logger.error("Exception occurred while generating x-pay-token {}", e);
            throw new XPayTokenException(e);
        }
    }

    /**
     *
     * @param bytes
     * @return
     */
    private  String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    public String generateXpayToken (String resourcePath, String queryString, String requestBody, String sharedSecret) throws XPayTokenException {

        if (resourcePath != null && resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
            logger.info("'/' is not required. truncated the symbol to generate x-pay-token ");
        }
        if (queryString != null && queryString.startsWith("?")) {
            queryString = queryString.substring(1);
            logger.info("'?' is not required. truncated the symbol to generate x-pay-token ");
        }

        return get(resourcePath, queryString ,requestBody, sharedSecret);
    }
}
