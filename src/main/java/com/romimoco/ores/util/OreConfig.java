package com.romimoco.ores.util;

import com.romimoco.ores.Ores;
import com.romimoco.ores.proxy.CommonProxy;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Ores.MODID, name = "Romimoco/ores/"+ Ores.NAME)
public class OreConfig {

    //private static final String CATEGORY_GENERAL = "general";
    //private static final String CATEGORY_WORLDGEN = "worldgen";

    public static final Worldgen worldgen = new Worldgen();
    public static final Recipes recipes = new Recipes();
    public static final Integrations integrations = new Integrations();

        @Config.RequiresMcRestart
        @Comment("Generate Variants for ores")
        public static boolean genVariants;

        @Config.RequiresMcRestart
        @Comment("Generate dusts for each ore")
        public static boolean genDusts;

        @Config.RequiresMcRestart
        @Comment("Generate ingots for each ore")
        public static boolean genIngots;

        @Config.RequiresMcRestart
        @Comment("Generate full Blocks for each ore")
        public static boolean genFullBlocks;

        @Config.RequiresMcRestart
        @Comment("Generate tools for each ore")
        public static boolean genTools;

        @Config.RequiresMcRestart
        @Comment("Generate armor sets for each ore")
        public static boolean genArmor;

        @Config.RequiresMcRestart
        @Comment("Generate buckets for each ore")
        public static boolean genBuckets;

        @Config.RequiresMcRestart
        @Comment("Generate fluids for each ore")
        public static boolean genFluids;


        @Config.RequiresMcRestart
        @Comment("Generate Shields for each ore")
        public static boolean genShields;

        @Config.RequiresMcRestart
        @Comment("Create a resource pack for english localization")
        public static boolean createResourcePack;

        @Config.RequiresMcRestart
        @Comment("Require a resource pack to be installed for localizations to work.  Slight memory decrease and faster lookups")
        public static boolean requireResourcePack;

    public static class Worldgen {

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft iron ore")
        public  boolean disableVanillaIron;

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft coal ore")
        public  boolean disableVanillaCoal;

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft gold ore")
        public  boolean disableVanillaGold;

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft redstone ore")
        public static boolean disableVanillaRedstone;

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft diamonds")
        public boolean disableVanillaDiamond;

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft emeralds")
        public boolean disableVanillaEmerald;

        @Config.RequiresWorldRestart
        @Comment("Disable world generation of minecraft nether quartz")
        public boolean disableVanillaQuartz;
    }

    public static class Recipes {

        @Config.RequiresMcRestart
        @Comment("Add a recipe to create dusts by crafting the ore with obsidian, a piston and redstone")
        public boolean simpleDustRecipe;

        @Config.RequiresMcRestart
        @Comment("Make tool and armor recipes require full ingots rather ingot variants")
        public boolean recipesRequireIngot;

        @Config.RequiresMcRestart
        @Comment("Allow combining nuggets -> shards, shards -> chunks etc")
        public boolean variantCombinationRecipes;

    }

    public static class Integrations{
        @Comment("Add Tinkers Construct Smelting and Casting for all ores")
        public boolean TiConIntegration;

        @Comment("Add Thermal Expansion Pulverizer, Compactor and Magma Crucible recipes")
        public boolean TEIntegration;

        @Comment("Add Immersive Engineering Smelting and Crushing for all ores")
        public boolean IEIntegration;

    }

    @Mod.EventBusSubscriber(modid = Ores.MODID)
    private static class Handler
    {
        @SubscribeEvent(priority = EventPriority.NORMAL)
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(Ores.MODID))
            {
                ConfigManager.sync(Ores.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
