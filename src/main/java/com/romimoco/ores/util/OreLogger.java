package com.romimoco.ores.util;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OreLogger {
    private static Logger LOGGER;
    private static BufferedWriter lang;

    public static void init(FMLPreInitializationEvent event){
        LOGGER = event.getModLog();

        //Set up file for creating resource pack
        if(OreConfig.createResourcePack){
            try{
                lang = new BufferedWriter(new FileWriter(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/Romimoco/ores/en_us.lang")));
            }catch(IOException e){
                error(e.getMessage());
            }
        }

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

    public static void localize(String localization){
        if(!OreConfig.createResourcePack)
            return;
        try {
            lang.write(localization);
            lang.write("\n");
        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    public static void commitLocalization(){
        try {
            lang.flush();
        } catch (IOException e) {
            error(e.getMessage());
        }
    }
}
