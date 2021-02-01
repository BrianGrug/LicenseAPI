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
import java.util.Arrays;

public class Actions {

    private final String url = "http://daddyimpregnant.com:8000/licensing/";

    /**
     * Check if a license key is valid
     *
     * @param type          the type of the license key
     * @param license       the license key
     * @param securityToken the security token for the license
     * @param logType       the type of the logger
     * @param plugin        whether it's a plugin or not
     * @return the result
     */
    public LicenseResults checkLicense(String type, String license, String securityToken, LogType logType, boolean plugin) {
        final Logger logger = new Logger();
        final JsonObject jsonObject = JsonUtils.convertToJson(startCheck(type, license, securityToken == null ? "" : securityToken));

        final String result = jsonObject.get("result").getAsString();
        final String discord = jsonObject.get("discord").getAsString();

        if (!result.equals("Valid")) {
            if (plugin) {
                final Plugin main = Bukkit.getPluginManager().getPlugin(type);

                Bukkit.getScheduler().runTaskLater(main, () -> {
                    if (logType.equals(LogType.ENABLED)) {
                        Arrays.asList(
                                "[-----------------------------------------]",
                                "         Your license is invalid!",
                                "         Please create a ticket @",
                                "" + "https://discord.mizuledevelopment.com",
                                "[-----------------------------------------]",
                                "Your license is invalid! Please create a ticket @ https://discord.mizuledevelopment.com"
                        ).forEach(msg -> logger.log(2, msg));
                    }

                    Bukkit.getPluginManager().disablePlugin(main);
                }, 5L);
            }


            return LicenseResults.DENY;
        }

        if (logType.equals(LogType.ENABLED)) {
            if (plugin) {
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(type), () -> {
                    Arrays.asList(
                            "[-----------------------------------------]",
                            "      Your license has been verified!",
                            "         Your key is registered to:",
                            "             " + discord,
                            "[-----------------------------------------]"
                    ).forEach(msg -> logger.log(3, msg));
                }, 5L);
            }
        }
        
        return LicenseResults.ALLOW;
    }


    /**
     * Start checking if the license key is valid
     *
     * @param type          the type of the key
     * @param license       the key
     * @param securityToken the security token
     * @return the result of the check
     */
    private String startCheck(String type, String license, String securityToken) {
        try {
            final Connection.Response res1 = Jsoup.connect(this.url + "validate/")
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            return Jsoup.connect(this.url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("key", license, "software", type, "password", securityToken)
                    .header("X-CSRFToken", res1.cookie("csrftoken"))
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute().body();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "Error";
    }

    /**
     * Create a new license key
     *
     * @param discord       the discord username
     * @param plugin        the plugin for the license key
     * @param securityToken the security token
     * @return the license key
     */
    public String createLicense(String discord, String plugin, String securityToken) {
        try {
            final Connection.Response res1 = Jsoup.connect(this.url + "validate/")
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            return Jsoup.connect(this.url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("discord", discord, "software", plugin, "password", securityToken)
                    .header("X-CSRFToken", res1.cookie("csrftoken"))
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute().body();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "Error";
    }

    /**
     * Get a discord user from a license key
     *
     * @param license       the license key
     * @param type          the type of the license key
     * @param securityToken the security key for the license
     * @return the username
     */
    private String getDiscordFromLicense(String license, String type, String securityToken) {
        try {
            final Connection.Response res1 = Jsoup.connect(this.url + "discord")
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();

            return Jsoup.connect(this.url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("key", license, "software", type, "password", null)
                    .header("X-CSRFToken", res1.cookie("csrftoken"))
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "Error";
    }

    /**
     * Get a license key from a discord username
     *
     * @param discord       the discord username
     * @param type          the type of the license key
     * @param securityToken the security token for the license
     * @return the license key
     */
    public String getLicenseFromDiscord(String discord, String type, String securityToken) {
        try {
            final Connection.Response res1 = Jsoup.connect(url + "discord")
                    .method(Connection.Method.GET)
                    .userAgent("Mozilla")
                    .header("X-CSRFToken", "")
                    .header("Connection", "close")
                    .execute();


            return Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla")
                    .data("key", discord, "software", type, "password", "")
                    .header("X-CSRFToken", res1.cookie("csrftoken"))
                    .header("Connection", "close")
                    .cookies(res1.cookies())
                    .execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "Error";
    }
}