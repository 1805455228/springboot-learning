package com.hins.sp14elasticsearch.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author qixuan.chen
 * @date 2019-09-17 20:19
 */
public class JsonValidator {


    public static boolean validate(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

}
