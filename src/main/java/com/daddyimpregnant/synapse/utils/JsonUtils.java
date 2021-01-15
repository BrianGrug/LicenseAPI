package com.daddyimpregnant.synapse.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonUtils {

    public static JsonObject convertToJson(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, JsonObject.class);
    }
}
