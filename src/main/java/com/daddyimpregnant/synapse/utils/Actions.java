package com.daddyimpregnant.synapse.utils;

import com.daddyimpregnant.synapse.enums.LicenseResults;
import com.daddyimpregnant.synapse.enums.LogType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Actions {
    public LicenseResults checkLicense(String type, String license, String securityToken, LogType logType, boolean plugin) {
        Logger logger = new Logger();

        if(securityToken == null){
            securityToken = "17011525";
        }

        Gson gson = new Gson();
        JsonObject jsonObject = JsonUtils.convertToJson(startCheck(type, license, securityToken));


        String ip = jsonObject.get("ip").getAsString();
        String date = jsonObject.get("date").getAsString();
        String result = jsonObject.get("result").getAsString();

        if(!result.equals("Valid")){
            if(logType.equals(LogType.ENABLED)){
                logger.log(2, "Your license is invalid! Please contact me on Discord @ DaddyImPregnant#5192");
            }

            Plugin main = Bukkit.getPluginManager().getPlugin(type);

            if(plugin){
                Bukkit.getScheduler().runTaskLater(main, () -> Bukkit.getPluginManager().disablePlugin(main),25L);
            }
            return LicenseResults.DENY;
        }
        String discord = jsonObject.get("discord").getAsString();
        if(logType.equals(LogType.ENABLED)){
            logger.log(3, "[-----------------------------------------]");
            logger.log(3, "      Your license has been verified!");
            logger.log(3, "         Your key is registered to:");
            logger.log(3, "             " + discord);
            logger.log(3, "[-----------------------------------------]");
        }
        return LicenseResults.ALLOW;
    }
    private String startCheck(String type, String license, String securityToken) {

        String token;
        String url = "http://daddyimpregnant.com:8000/licensing/validate/";

        try {
            Connection.Response res1 = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            token = res1.cookie("csrftoken");

            Connection.Response res2 = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("key", license, "software", type, "password", securityToken)
                    .header("X-CSRFToken", token)
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute();
            return res2.body();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Error";
    }
    public String createLicense(String discord, String plugin, String securityToken) {

        String token;
        String url = "http://daddyimpregnant.com:8000/licensing/add/";

        try {
            Connection.Response res1 = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            token = res1.cookie("csrftoken");

            Connection.Response res2 = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("discord", discord, "software", plugin, "password", securityToken)
                    .header("X-CSRFToken", token)
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute();

            return res2.body();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Error";
    }
    private String getDiscordFromLicense(String license, String type, String securityToken) {

        String token;
        String url = "http://daddyimpregnant.com:8000/licensing/discord/";

        try {
            Connection.Response res1 = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            token = res1.cookie("csrftoken");

            Connection.Response res2 = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("key", license, "software", type, "password", null)
                    .header("X-CSRFToken", token)
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute();

            return res2.body();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Error";
    }
    public String getLicenseFromDiscord(String discord, String type, String securityToken) {

        String token;
        String url = "http://192.168.1.13:8000/licensing/discord/";

        try {
            Connection.Response res1 = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            token = res1.cookie("csrftoken");


            Connection.Response res2 = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("key", discord, "software", type, "password", "")
                    .header("X-CSRFToken", token)
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute();

            return res2.body();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Error";
    }
}
