package com.craftorio.ores.blocks;

import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.craftorio.ores.world.OreGenBase;
import com.craftorio.ores.world.OreGenDefinition;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;


public class ModBlocks {

    public static LinkedList<BaseOre> ORES = new LinkedList<>();
    public static HashMap<String, BaseGemOre> GEMS = new HashMap();
    public static HashMap<String, Block> BLOCKS = new HashMap<>();


    public static void init(FMLPreInitializationEvent event) {
        //Read JSON here, init blocks
        JsonParser parser = new JsonParser();
        JsonObject oreDefs;

        OreGenBase generator = new OreGenBase();

        try {
            oreDefs = (JsonObject) parser.parse(new FileReader(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/Craftorio/ores/oreDefinitions.json")));
        } catch (FileNotFoundException e) {
            OreLogger.error("No Ore-Definitions found.  Create an oreDefinitions.json file in config/Craftorio/ores to define custom ores.");
            return;
        }
        OreLogger.debug("Found ore-defs");
        JsonArray oreArray = (JsonArray) oreDefs.get("OreList");

        if (oreArray != null) {
            for (JsonElement j : oreArray) {
                String name = ((JsonObject) j).get("Name").getAsString();
                OreLogger.debug("Initializing " + name);
                BaseOre ore = new BaseOre((JsonObject) j);
                ORES.push(ore);
                if (null != ore.oreGenDefinition) {
                    generator.add(ore.oreGenDefinition);
                }
                if (ore.genFullBlocks) {
                    BLOCKS.put(ore.name + "Block", new BaseBlock(ore));
                }
            }
        }

        JsonArray gemArray = (JsonArray) oreDefs.get("GemList");
        if (gemArray != null) {
            for (JsonElement j : gemArray) {
                String name = ((JsonObject) j).get("Name").getAsString();
                OreLogger.debug("Initializing " + name);

                GEMS.put(name, new BaseGemOre((JsonObject) j));

                JsonObject OreGen = ((JsonObject) j).getAsJsonObject("Generation");
                if (OreGen != null) {
                    generator.add(new OreGenDefinition(GEMS.get(name), OreGen));
                } else {
                    (GEMS.get(name)).shouldRegister = false;
                }

                if (OreConfig.genFullBlocks && ((JsonObject) j).get("Drops") != null && ((JsonObject) j).get("Drops").toString().charAt(0) == '{') {
                    BLOCKS.put(name + "Block", new BaseBlock(GEMS.get(name)));
                }
            }
        }
        GameRegistry.registerWorldGenerator(generator, 0);

    }

    public static void postInit() {
    }
}

