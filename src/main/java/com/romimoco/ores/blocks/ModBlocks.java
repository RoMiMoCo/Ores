package com.romimoco.ores.blocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
import com.romimoco.ores.world.OreGenBase;
import com.romimoco.ores.world.OreGenDefinition;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;


public class ModBlocks {

    public static LinkedList<Block> ORES = new LinkedList<>();
    public static HashMap<String, BaseGemOre> GEMS = new HashMap();
    public static HashMap<String, Block> BLOCKS = new HashMap<>();


    public static void init(FMLPreInitializationEvent event) {
        //Read JSON here, init blocks
        JsonParser parser = new JsonParser();
        JsonObject oreDefs;

        OreGenBase generator = new OreGenBase();

        try {
            oreDefs = (JsonObject) parser.parse(new FileReader(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/Romimoco/ores/oreDefinitions.json")));
        } catch (FileNotFoundException e) {
            OreLogger.error("No Ore-Definitions found.  Create an oreDefinitions.json file in config/Romimoco/ores to define custom ores.");
            return;
        }
        OreLogger.debug("Found ore-defs");
        JsonArray oreArray = (JsonArray) oreDefs.get("OreList");

        if (oreArray != null) {
            for (JsonElement j : oreArray) {
                String name = ((JsonObject) j).get("Name").getAsString();
                OreLogger.debug("Initializing " + name);

                if (OreConfig.genVariants) {
                    ORES.push(new BaseOreWithVariants((JsonObject) j));
                } else {
                    ORES.push(new BaseOre((JsonObject) j));
                }
                JsonObject OreGen = ((JsonObject) j).getAsJsonObject("Generation");
                if (OreGen != null) {
                    generator.add(new OreGenDefinition((BaseOre) ORES.peek(), OreGen));
                } else {
                    ((BaseOre) ORES.peek()).shouldRegister = false;//do not register ore blocks for ores that shouldn't generate
                }
                if (OreConfig.genFullBlocks) {
                    BLOCKS.put(((BaseOre) ORES.peek()).name + "Block", new BaseBlock((BaseOre) ORES.peek()));
                }
            }
        }

        JsonArray gemArray = (JsonArray) oreDefs.get("GemList");
        if (gemArray != null) {
            for (JsonElement j : gemArray) {
                String name = ((JsonObject) j).get("Name").getAsString();
                OreLogger.debug("Initializing " + name);

                if (OreConfig.genVariants) {
                    GEMS.put(name, new BaseGemOreWithVariants((JsonObject) j));
                } else {
                    GEMS.put(name, new BaseGemOre((JsonObject) j));
                }

                JsonObject OreGen = ((JsonObject) j).getAsJsonObject("Generation");

                if (OreGen != null) {
                    generator.add(new OreGenDefinition((BaseOre) GEMS.get(name), OreGen));
                } else {
                    ((BaseOre) GEMS.get(name)).shouldRegister = false;
                }

                if (OreConfig.genFullBlocks && ((JsonObject) j).get("Drops") != null && ((JsonObject) j).get("Drops").toString().charAt(0) == '{') {
                    BLOCKS.put(name + "Block", new BaseBlock((BaseGemOre) GEMS.get(name)));
                }
            }
        }
        GameRegistry.registerWorldGenerator(generator, 0);

    }

    public static void postInit() {
    }

}

