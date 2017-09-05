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

        @Comment("Generate Variants for ores")
        public static boolean genVariants;

        @Comment("Generate dusts for each ore")
        public static boolean genDusts;

        @Comment("Generate ingots for each ore")
        public static boolean genIngots;

        @Comment("Generate full Blocks for each ore")
        public static boolean genFullBlocks;

        @Comment("Generate tools for each ore")
        public static boolean genTools;

        @Comment("Generate armor sets for each ore")
        public static boolean genArmor;

        @Comment("Create a resource pack for english localization")
        public static boolean createResourcePack;

    public static class Worldgen {

        @Comment("Disable world generation of minecraft iron ore")
        public  boolean disableVanillaIron;
        @Comment("Disable world generation of minecraft coal ore")
        public  boolean disableVanillaCoal;
        @Comment("Disable world generation of minecraft gold ore")
        public  boolean disableVanillaGold;
        @Comment("Disable world generation of minecraft redstone ore")
        public static boolean disableVanillaRedstone;
        @Comment("Disable world generation of minecraft diamonds")
        public boolean disableVanillaDiamond;
        @Comment("Disable world generation of minecraft emeralds")
        public boolean disableVanillaEmerald;
        @Comment("Disable world generation of minecraft nether quartz")
        public boolean disableVanillaQuartz;
    }

    public static class Recipes {
        @Comment("Add a recipe to create dusts by crafting the ore with cobblestone")
        public boolean simpleDustRecipe;

        @Comment("Allow combining nuggets -> shards, shards -> chunks etc")
        public boolean variantCombinationRecipes;

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
