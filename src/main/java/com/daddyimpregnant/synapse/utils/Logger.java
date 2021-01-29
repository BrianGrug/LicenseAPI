package com.daddyimpregnant.synapse.utils;

import com.daddyimpregnant.synapse.Synapse;
import org.bukkit.Bukkit;
import org.fusesource.jansi.Ansi;

import java.util.logging.Level;

public class Logger {

    /**
     * Method for logging a message to the terminal
     *
     * @param type    the urgency of the message
     * @param message the message
     */
    public void log(int type, String message) {
        final java.util.logging.Logger logger = Synapse.getInstance().isPlugin()
                ? Bukkit.getLogger()
                : java.util.logging.Logger.getLogger(Logger.class.getName());

        final Ansi ansi = type == 1
                ? Ansi.ansi().fg(Ansi.Color.YELLOW).boldOff()
                : type == 2
                ? Ansi.ansi().fg(Ansi.Color.RED).boldOff()
                : Ansi.ansi().fg(Ansi.Color.GREEN).boldOff();

        logger.log(type == 1 ? Level.INFO : type == 2 ? Level.SEVERE : Level.INFO, ansi.toString() + message + Ansi.ansi().reset());
    }
}