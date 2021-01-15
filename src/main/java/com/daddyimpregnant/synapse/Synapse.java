package com.daddyimpregnant.synapse;

import com.daddyimpregnant.synapse.enums.LicenseResults;
import com.daddyimpregnant.synapse.enums.LogType;
import com.daddyimpregnant.synapse.utils.Actions;
import lombok.Getter;
import lombok.Setter;

public class Synapse {

    @Setter
    @Getter
    private LicenseResults licenseResults;
    @Setter
    @Getter
    private LogType logType;
    @Getter
    boolean plugin;
    @Getter
    private static Synapse instance;

    public Synapse() {
        instance = this;
    }

    public LicenseResults checkLicense(String type, String license, String securityToken, LogType logType, boolean plugin) {
        Actions actions = new Actions();
        return actions.checkLicense(type, license, securityToken, logType, plugin);
    }

    public String createLicense(String discord, String plugin, String securityToken) {
        Actions actions = new Actions();
        return actions.createLicense(discord, plugin, securityToken);
    }

    public String getLicenseFromDiscord(String discord, String type, String securityToken) {
        Actions actions = new Actions();
        return actions.getLicenseFromDiscord(discord, type, securityToken);
    }
}