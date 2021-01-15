package com.daddyimpregnant.synapse.utils;

import com.daddyimpregnant.synapse.Synapse;
import org.bukkit.Bukkit;
import org.fusesource.jansi.Ansi;

public class Logger {

    public void log(int type, String message) {
        if (Synapse.getInstance().isPlugin()) {
            if (type == 1) {
                Bukkit.getLogger().warning(Ansi.ansi().fg(Ansi.Color.YELLOW).boldOff().toString() + message + Ansi.ansi().reset());
            }
            if (type == 2) {
                Bukkit.getLogger().severe(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + message + Ansi.ansi().reset());
            }
            if (type == 3) {
                Bukkit.getLogger().info(Ansi.ansi().fg(Ansi.Color.GREEN).boldOff().toString() + message + Ansi.ansi().reset());
            }
        } else {
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());
            if (type == 1) {
                logger.warning(Ansi.ansi().fg(Ansi.Color.YELLOW).boldOff().toString() + message + Ansi.ansi().reset());
            }
            if (type == 2) {
                logger.severe(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + message + Ansi.ansi().reset());
            }
            if (type == 3) {
                logger.info(Ansi.ansi().fg(Ansi.Color.GREEN).boldOff().toString() + message + Ansi.ansi().reset());
            }
        }
    }
}