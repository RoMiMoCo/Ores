package com.romimoco.ores.proxy;

import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.integrations.IOreIntegration;
import com.romimoco.ores.integrations.OreIntegrations;
import com.romimoco.ores.lang.i18n;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
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

        //All items are created, can write the .lang file now if needed
        if (OreConfig.createResourcePack) {
            OreLogger.commitLocalization();
        }

        if (!OreConfig.requireResourcePack) {
            //init the i18n system
            langs = new i18n(event);
        }

        //All items are created, can integrate them now
        for (IOreIntegration i : OreIntegrations.integrations) {
            i.preInit(event);
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
