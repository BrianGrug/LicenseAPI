package com.daddyimpregnant.synapse.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonUtils {



    public static JsonObject convertToJson(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, JsonObject.class);
    }


/*    public static JsonObject convertToJson(String string) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(string, JsonObject.class).getAsJsonArray();
        return jsonArray.get(0).getAsJsonObject();
    }*/
}
