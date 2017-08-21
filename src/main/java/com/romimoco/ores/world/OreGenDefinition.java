package com.romimoco.ores.world;

import com.google.gson.JsonObject;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.OreLogger;

public class OreGenDefinition {
    public BaseOre ore;
    public int minY;
    public int maxY;
    public int spawnChance;
    public int veinSize;


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
    }
}
