package com.daddyimpregnant.synapse;

import com.daddyimpregnant.synapse.enums.LicenseResults;
import com.daddyimpregnant.synapse.enums.LogType;
import com.daddyimpregnant.synapse.utils.Actions;
import lombok.Getter;
import lombok.Setter;

public class Synapse {

    @Setter @Getter private LicenseResults licenseResults;
    @Setter @Getter private LogType logType;
    @Getter boolean plugin;
    @Getter private static Synapse instance;

    public Synapse() {
        instance = this;
    }

    /**
     * @param type Software name (zHub, Portal etc)
     * @param license License key as a string
     * @param securityToken The users security token for the software
     * @param logType Should the console log information?
     * @param plugin Is the software a plugin?
     * @return True / false depending on the params
     */
    public LicenseResults checkLicense(String type, String license, String securityToken, LogType logType, boolean plugin) {
        Actions actions = new Actions();
        return actions.checkLicense(type, license, securityToken, logType, plugin);
    }

    /**
     * @param discord The users discord
     * @param plugin The software to generate the license for
     * @param securityToken The security token for the software
     * @return A created license as a string
     */
    public String createLicense(String discord, String plugin, String securityToken) {
        Actions actions = new Actions();
        return actions.createLicense(discord, plugin, securityToken);
    }

    /**
     * @param discord The users discord
     * @param type The type of software
     * @param securityToken The security token to check
     * @return The license as a string if it's not null
     */
    public String getLicenseFromDiscord(String discord, String type, String securityToken) {
        Actions actions = new Actions();
        return actions.getLicenseFromDiscord(discord, type, securityToken);
    }
}