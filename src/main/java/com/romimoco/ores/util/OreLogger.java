package com.romimoco.ores.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class OreLogger {
    private static Logger LOGGER;

    public static void init(Logger in){
        LOGGER = in;
    }

    public static void trace(String msg){
        LOGGER.log(Level.TRACE, msg);
    }
    public static void debug(String msg){
        LOGGER.log(Level.DEBUG, msg);
    }
    public static void info(String msg){
        LOGGER.log(Level.INFO, msg);
    }
    public static void warn(String msg){
        LOGGER.log(Level.WARN, msg);
    }
    public static void error(String msg){
        LOGGER.log(Level.ERROR, msg);
    }
    public static void fatal(String msg){
        LOGGER.log(Level.FATAL, msg);
    }
}
