package com.romimoco.ores;

import com.romimoco.ores.events.OreGenEventHandler;
import com.romimoco.ores.events.RegistryEventHandler;
import com.romimoco.ores.integrations.IEIntegration;
import com.romimoco.ores.integrations.OreIntegrations;
import com.romimoco.ores.integrations.TEIntegration;
import com.romimoco.ores.integrations.TiConIntegration;
import com.romimoco.ores.proxy.CommonProxy;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
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


@Mod(modid = Ores.MODID, version = Ores.VERSION, name = Ores.NAME, acceptedMinecraftVersions = "[1.15, 1.16)")
public class Ores
{
    public static final String MODID = "romimocoores";
    public static final String NAME = "ores";
    public static final String VERSION = "0.4.2";


    @Mod.Instance
    public static Ores instance;

    @SidedProxy(clientSide = "com.romimoco.ores.proxy.ClientProxy", serverSide = "com.romimoco.ores.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        OreLogger.init(event);
        OreLogger.info("Romimoco ores PRE-INIT");

        //Set up all of the integrations here
        if(OreConfig.integrations.TiConIntegration){
            if(Loader.isModLoaded("tconstruct")){
                OreLogger.info("Integrating with TiCon");
                OreIntegrations.addIntegration(new TiConIntegration());
            }else{
                OreLogger.error("Tinkers construct integration requested but Tinkers is not loaded");
            }
        }

        if(OreConfig.integrations.IEIntegration){
            if(Loader.isModLoaded("immersiveengineering")){
                OreLogger.info("Integrating with Immersive Engineering");
                OreIntegrations.addIntegration(new IEIntegration());
            }else{
                OreLogger.error("Immersive Engineering integration requested but Immersive Engineering is not loaded");
            }
        }
        if(OreConfig.integrations.TEIntegration){
            if(Loader.isModLoaded("thermalexpansion")){
                OreLogger.info("Integrating with Thermal Expansion");
                OreIntegrations.addIntegration(new TEIntegration());
            }else{
                OreLogger.error("Thermal Expansion integration requested but Thermal Expansion is not loaded");
            }
        }

        proxy.preInit(event);

        MinecraftForge.EVENT_BUS.register(new RegistryEventHandler());
        MinecraftForge.ORE_GEN_BUS.register(new OreGenEventHandler());


        //0.2.0
        //TODO: Fluids

    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        OreLogger.info("Romimoco ores INIT");
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
        IForgeRegistry<IRecipe> recipes = ForgeRegistries.RECIPES;
        OreLogger.info("Romimoco ores POST-INIT");
        proxy.postInit(event);
    }

}
