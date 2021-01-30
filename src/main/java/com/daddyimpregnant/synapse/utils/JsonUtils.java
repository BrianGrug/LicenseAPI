package com.daddyimpregnant.synapse.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonUtils {

    private final static Gson gson = new Gson();

    /**
     * Method for getting a {@link JsonObject} from a {@link String}
     *
     * @param string the string to get the json object from
     * @return the json object
     */
    public static JsonObject convertToJson(String string) {
        return gson.fromJson(string, JsonObject.class);
    }
}
