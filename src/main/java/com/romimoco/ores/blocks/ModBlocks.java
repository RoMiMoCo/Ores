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
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;


public class ModBlocks {

    public static LinkedList<Block> ORES = new LinkedList<>();
    public static HashMap<String, Block> BLOCKS = new HashMap<>();


    public static void init(){
        //Read JSON here, init blocks
        JsonParser parser = new JsonParser();
        JsonObject oreDefs;

        OreGenBase generator = new OreGenBase();

        try {
            oreDefs = (JsonObject) parser.parse(new FileReader(new File("config/Romimoco/ores/oreDefinitions.json")));
        } catch (FileNotFoundException e) {
           OreLogger.error("No Ore-Definitions found.  Create an oreDefinitions.json file in config/Romimoco/ores to define custom ores.");
           return;
        }
        OreLogger.debug("Found ore-defs");
        JsonArray oreArray = (JsonArray) oreDefs.get("OreList");

        for(JsonElement j : oreArray){
            String name = ((JsonObject)j).get("Name").getAsString();
            OreLogger.debug("Initializing " +name);

            if(OreConfig.genVariants){
                ORES.push(new BaseOreWithVariants((JsonObject)j));
            }else {
                ORES.push(new BaseOre((JsonObject) j));
            }
            JsonObject OreGen = ((JsonObject)j).getAsJsonObject("Generation");
            generator.add(new OreGenDefinition((BaseOre) ORES.peek(), OreGen));

            BLOCKS.put(((BaseOre) ORES.peek()).name + "Block", new BaseBlock((BaseOre)ORES.peek()));
        }
        GameRegistry.registerWorldGenerator(generator, 0);

    }

    public static void postInit(){
    }

}

