package com.craftorio.ores;

import com.craftorio.ores.integrations.*;
import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.events.OreGenEventHandler;
import com.craftorio.ores.events.RegistryEventHandler;
import com.craftorio.ores.proxy.CommonProxy;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;


@Mod(modid = Ores.MODID, version = Ores.VERSION, name = Ores.NAME, acceptedMinecraftVersions = "[1.12, 1.13)")
public class Ores {
    public static final String MODID = "ores";
    public static final String NAME = "ores";
    public static final String VERSION = "0.5.3";


    @Mod.Instance
    public static Ores instance;

    @SidedProxy(clientSide = "com.craftorio.ores.proxy.ClientProxy", serverSide = "com.craftorio.ores.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        OreLogger.init(event);
        OreLogger.info("Craftorio ores PRE-INIT");

        //Set up all of the integrations here
        if (OreConfig.integrations.TiConIntegration) {
            if (Loader.isModLoaded("tconstruct")) {
                OreLogger.info("Integrating with TiCon");
                OreIntegrations.addIntegration(new TiConIntegration());
            } else {
                OreLogger.error("Tinkers construct integration requested but Tinkers is not loaded");
            }
        }
        if (OreConfig.integrations.IC2Integration) {
            if (Loader.isModLoaded("ic2")) {
                OreLogger.info("Integrating with Industrial Craft 2");
                OreIntegrations.addIntegration(new IC2Integration());
            } else {
                OreLogger.error("Industrial Craft 2 integration requested but Industrial Craft 2 is not loaded");
            }
        }
        if (OreConfig.integrations.IEIntegration) {
            if (Loader.isModLoaded("immersiveengineering")) {
                OreLogger.info("Integrating with Immersive Engineering");
                OreIntegrations.addIntegration(new IEIntegration());
            } else {
                OreLogger.error("Immersive Engineering integration requested but Immersive Engineering is not loaded");
            }
        }
        if (OreConfig.integrations.TEIntegration) {
            if (Loader.isModLoaded("thermalexpansion")) {
                OreLogger.info("Integrating with Thermal Expansion");
                OreIntegrations.addIntegration(new TEIntegration());
            } else {
                OreLogger.error("Thermal Expansion integration requested but Thermal Expansion is not loaded");
            }
        }

        if (Loader.isModLoaded("jeresources")) {
            OreLogger.info("Integrating with Just Enough Resources");
            OreIntegrations.addIntegration(new JERIntegration());
        }

        proxy.preInit(event);

        MinecraftForge.EVENT_BUS.register(new RegistryEventHandler());
        MinecraftForge.ORE_GEN_BUS.register(new OreGenEventHandler());


        //0.2.0
        //TODO: Fluids

    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreLogger.info("Craftorio ores INIT");
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        IForgeRegistry<IRecipe> recipes = ForgeRegistries.RECIPES;
        OreLogger.info("Craftorio ores POST-INIT");
        proxy.postInit(event);
    }

}
