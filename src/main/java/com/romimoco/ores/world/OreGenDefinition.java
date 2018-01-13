package com.romimoco.ores.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.OreLogger;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;

public class OreGenDefinition {
    public BaseOre ore;
    public int minY;
    public int maxY;
    public int spawnChance;
    public int veinSize;
    public int[] rarities;
    public ArrayList<String> biomes;


    public OreGenDefinition(BaseOre ore, int minY, int maxY, int spawnChance, int veinSize){
        this.ore = ore;
        this.minY = minY;
        this.maxY = maxY;
        this.spawnChance = spawnChance;
        this.veinSize = veinSize;

    }


    public OreGenDefinition(BaseOre ore, JsonObject def){
        this.ore = ore;

        //default values for if json parsing fails
        this.minY = 0;
        this.maxY = 16;
        this.spawnChance = 4;
        this.veinSize = 64;
        this.rarities = new int[]{20,40,60,80,100};//equal chance to spawn
        this.biomes = null;

        if(def == null)
            return; //no ore gen specified

        //Try to parse json
        try {
            this.minY = def.get("MinY").getAsInt();
        }catch(Exception e){OreLogger.error("No such element MinY");}

        try {
            this.maxY = def.get("MaxY").getAsInt();
        }catch(Exception e){OreLogger.error("No such element MaxY");}

        try {
            this.spawnChance = def.get("SpawnChance").getAsInt();
        }catch(Exception e){OreLogger.error("No such element SpawnChance");}

        try{
            this.veinSize = def.get("VeinSize").getAsInt();
        }catch(Exception e){OreLogger.error("No such element VeinSize");}

        //Ore Rarities
        try{
            JsonObject Rarities = (JsonObject) def.get("Rarities");
            int poorPercent = 0;
            int lowPercent = 0;
            int moderatePercent = 0;
            int highPercent = 0;
            int richPercent = 0;
            try{
                poorPercent = Rarities.get("Poor").getAsInt();
            } catch (Exception e) {OreLogger.error("No rarity defined for poor "+ this.ore.name +" ore, none will spawn.  Is this intended?");}
            try{
                lowPercent = Rarities.get("Low").getAsInt();
            } catch (Exception e) {OreLogger.error("No rarity defined for low-value "+ this.ore.name +" ore, none will spawn.  Is this intended?");}
            try{
                moderatePercent = Rarities.get("Moderate").getAsInt();
            } catch (Exception e) {OreLogger.error("No rarity defined for moderate-value "+ this.ore.name +" ore, none will spawn.  Is this intended?");}
            try{
                highPercent = Rarities.get("High").getAsInt();
            } catch (Exception e) {OreLogger.error("No rarity defined for high-value "+ this.ore.name +" ore, none will spawn.  Is this intended?");}
            try{
                richPercent = Rarities.get("Rich").getAsInt();
            } catch (Exception e) {OreLogger.error("No rarity defined for rich "+ this.ore.name +" ore, none will spawn.  Is this intended?");}

            int total = poorPercent + lowPercent + moderatePercent + highPercent + richPercent;

            if(total != 100){ //someone can't do percentages
                double newtotal = 100/total;
                poorPercent = (int)(newtotal * poorPercent);
                lowPercent = (int)(newtotal * lowPercent);
                moderatePercent = (int)(newtotal * moderatePercent);
                highPercent = (int)(newtotal * highPercent);
                richPercent = (int)(newtotal * richPercent);
            }

            this.rarities[0] = poorPercent;
            this.rarities[1] = poorPercent + lowPercent;
            this.rarities[2] = poorPercent + lowPercent + moderatePercent;
            this.rarities[3] = poorPercent + lowPercent + moderatePercent + highPercent;
            this.rarities[4] = poorPercent + lowPercent + moderatePercent + highPercent + richPercent;
        }catch(Exception e){OreLogger.error("No rarities defined");}

        try{
            JsonArray biomes = def.get("Biomes").getAsJsonArray();
            ArrayList localBiomes =  new ArrayList<>();
            for(JsonElement j : biomes){
                try{
                    String biome = j.getAsString();
                    localBiomes.add(j.getAsString());
                }catch (Exception e){
                    e.printStackTrace();
                    OreLogger.error("Malformed biome name");}
            }
            if(!localBiomes.isEmpty()){
                this.biomes = localBiomes;
            }
        }catch (Exception e){OreLogger.error("No biomes specified, "+ this.ore.name +" ore will generate in all biomes");}
    }

    //Takes an int between 0 and 100
    public int getMetaToSpawn(int randIn){
        int i;
        for(i = 0; i < this.rarities.length; i++){
            if(this.rarities[i] > randIn){
                return 4 - i;
            }
        }
        if(this.rarities[i] != this.rarities[i-1]){ //if the last two rarities are equal, then the final was 0 and we don't want to spawn it
            return 4 - i;
        }else{
            return 4 -(i-1); //so instead spawn the previous one
        }
    }
}
