package com.monitoratec.tokenservice.vtswalletservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.UUID;


public class WalletServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceUtil.class);

    private static final SecureRandom random = new SecureRandom();
    private static final String alphaNumer = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     *
     * @param len
     * @return
     */
    public static String generateClientDeviceID(int len, String deviceUniqueIdentifier) {
        return String.valueOf(UUID.nameUUIDFromBytes(deviceUniqueIdentifier.getBytes()).getMostSignificantBits()).replace("-","");
    }

    /**
     *
     * @return
     */
    public static String generateXRequestID() {
      return UUID.randomUUID().toString();
    }

    /**
     *
     * @param provisioning
     * @return

    public static JsonArray getRiskDataJson(ProvisioningPayload provisioning) {
        JsonArray jsonArray = new JsonArray();
        ReflectionUtils.doWithFields(provisioning.getEncRiskDataInfo().getClass(), field -> {
            field.setAccessible(true);
            Object value = field.get(provisioning.getEncRiskDataInfo());
            if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
                JsonObject riskData = new JsonObject();
                riskData.addProperty("name", field.getName());
                riskData.addProperty("value", String.valueOf(value));
                jsonArray.add(riskData);
            }

        });

        return jsonArray;
    }*/

    /**
     *
     * @param input
     * @return

    public static String hash(String input) {
        byte[] _inputBytes = DigestUtils.sha256(input);
        return Base64.encodeBase64URLSafeString(_inputBytes);
    }*/

    /**
     * To generate random alpha numeric string based on the length
     * @param length
     * @return
     */
    private static String random ( int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(alphaNumer.charAt(random.nextInt(alphaNumer.length())));
        return sb.toString();
    }


}
