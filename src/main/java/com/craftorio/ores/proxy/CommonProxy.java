package com.craftorio.ores.proxy;

import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.integrations.IOreIntegration;
import com.craftorio.ores.integrations.OreIntegrations;
import com.craftorio.ores.lang.i18n;
import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.Items.ModItems;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    public i18n langs;

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init(event);
        ModItems.init();

        //All items are created, can integrate them now
        for (IOreIntegration i : OreIntegrations.integrations) {
            i.preInit(event);
        }

        //All items are created, can write the .lang file now if needed
        if (OreConfig.createResourcePack) {
            OreLogger.commitLocalization();
        }

        if (!OreConfig.requireResourcePack) {
            //init the i18n system
            langs = new i18n(event);
        }
    }


    public void init(FMLInitializationEvent event) {

        for (IOreIntegration i : OreIntegrations.integrations) {
            i.Init(event);
        }
    }


    public void postInit(FMLPostInitializationEvent event) {

        for (IOreIntegration i : OreIntegrations.integrations) {
            i.postInit(event);
        }
    }

}
