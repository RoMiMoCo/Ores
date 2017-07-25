package com.romimoco.ores.proxy;

import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.ModBlocks;
//import com.romimoco.ores.util.Config;
import com.romimoco.ores.util.OreConfig;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        //open and read config
        //File directory = event.getModConfigurationDirectory();
        //config = new Configuration(new File(directory.getPath(), "romimocoores.cfg"));
        //OreConfig.readConfig();



        ModBlocks.init();
        ModItems.init();
        //ModRecipes.init();

    }


    public void init(FMLInitializationEvent event) {


    }


    public void postInit(FMLPostInitializationEvent event) {
        //if(config.hasChanged()){
        //    config.save();
        //}

    }

}
