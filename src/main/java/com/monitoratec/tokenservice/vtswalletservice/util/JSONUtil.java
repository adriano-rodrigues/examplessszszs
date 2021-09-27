package com.monitoratec.tokenservice.vtswalletservice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JSONUtil {

    private static Gson gson;

    /**
     *
     * @param any
     * @param <Any>
     * @return
     */
    public static <Any> String toJson (Any any) {
        if (gson == null)
            gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(any);
    }
}

