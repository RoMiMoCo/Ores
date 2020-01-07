package com.craftorio.ores.world;

import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.enums.EnumOreValue;
import com.craftorio.ores.util.OreLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.ArrayList;

public class OreGenDimDefinition {
    public BaseOre ore;
    public int dimId;
    public int dimLike;
    public int minY = 0;
    public int maxY = 255;
    public int spawnChance = 40;
    public int veinSize = 4;
    public int[] rarities;
    public ArrayList<String> biomes;
    final public static ArrayList<Integer> supportedDims = new ArrayList<Integer>() {{
        add(DimensionType.OVERWORLD.getId());
        add(DimensionType.NETHER.getId());
        add(DimensionType.THE_END.getId());
    }};

    private int getJsonInt(JsonObject ob, String k, int def)
    {
        try {
            return ob.has(k) ? ob.get(k).getAsInt() : def;
        } catch (Exception e) {
            return def;
        }
    }

    private int getJsonInt(JsonObject ob, String k)
    {
        return ob.get(k).getAsInt();
    }

    public OreGenDimDefinition(BaseOre ore, JsonObject def) {
        if (def == null)
            return; //no ore gen specified

        this.ore = ore;
        //default values for if json parsing fails
        this.minY = getJsonInt(def, "MinY", this.minY);
        this.maxY = getJsonInt(def, "MaxY", this.maxY);
        this.spawnChance = getJsonInt(def, "SpawnChance", this.spawnChance);
        this.veinSize = getJsonInt(def, "VeinSize", this.veinSize);
        this.rarities = new int[]{20, 40, 60, 80, 100};//equal chance to spawn
        this.biomes = null;
        this.dimId = getJsonInt(def, "ID");
        this.dimLike = getJsonInt(def, "LikeID", (supportedDims.contains(dimId) ? dimId : DimensionType.OVERWORLD.getId()));

        //Ore Rarities
        try {
            JsonObject Rarities = (JsonObject) def.get("Rarities");
            int poorPercent = 0;
            int lowPercent = 0;
            int moderatePercent = 0;
            int highPercent = 0;
            int richPercent = 0;
            try {
                poorPercent = Rarities.get("Poor").getAsInt();
            } catch (Exception e) {
                OreLogger.debug("No rarity defined for poor " + this.ore.name + " ore, none will spawn.  Is this intended?");
            }
            try {
                lowPercent = Rarities.get("Low").getAsInt();
            } catch (Exception e) {
                OreLogger.debug("No rarity defined for low-value " + this.ore.name + " ore, none will spawn.  Is this intended?");
            }
            try {
                moderatePercent = Rarities.get("Moderate").getAsInt();
            } catch (Exception e) {
                OreLogger.debug("No rarity defined for moderate-value " + this.ore.name + " ore, none will spawn.  Is this intended?");
            }
            try {
                highPercent = Rarities.get("High").getAsInt();
            } catch (Exception e) {
                OreLogger.debug("No rarity defined for high-value " + this.ore.name + " ore, none will spawn.  Is this intended?");
            }
            try {
                richPercent = Rarities.get("Rich").getAsInt();
            } catch (Exception e) {
                OreLogger.debug("No rarity defined for rich " + this.ore.name + " ore, none will spawn.  Is this intended?");
            }

            int total = poorPercent + lowPercent + moderatePercent + highPercent + richPercent;

            if (total != 100) { //someone can't do percentages
                double newtotal = 100 / total;
                poorPercent = (int) (newtotal * poorPercent);
                lowPercent = (int) (newtotal * lowPercent);
                moderatePercent = (int) (newtotal * moderatePercent);
                highPercent = (int) (newtotal * highPercent);
                richPercent = (int) (newtotal * richPercent);
            }

            this.rarities[0] = poorPercent;
            this.rarities[1] = poorPercent + lowPercent;
            this.rarities[2] = poorPercent + lowPercent + moderatePercent;
            this.rarities[3] = poorPercent + lowPercent + moderatePercent + highPercent;
            this.rarities[4] = poorPercent + lowPercent + moderatePercent + highPercent + richPercent;
        } catch (Exception e) {
            OreLogger.debug("No rarities defined for " + this.ore.name + ", All rarities will spawn equally");
        }

        try {
            JsonArray biomes = def.get("Biomes").getAsJsonArray();
            ArrayList localBiomes = new ArrayList<>();
            for (JsonElement j : biomes) {
                try {
                    String biome = j.getAsString();
                    localBiomes.add(j.getAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                    OreLogger.error("Malformed biome name");
                }
            }
            if (!localBiomes.isEmpty()) {
                this.biomes = localBiomes;
            }
        } catch (Exception e) {
            OreLogger.debug("No biomes specified, " + this.ore.name + " ore will generate in all biomes");
        }
    }

    private int getValueToSpawn(int randIn)
    {
        int i;
        for (i = 0; i < this.rarities.length; i++) {
            if (this.rarities[i] > randIn) {
                return 4 - i;
            }
        }
        if (this.rarities[i] != this.rarities[i - 1]) { //if the last two rarities are equal, then the final was 0 and we don't want to spawn it
            return 4 - i;
        } else {
            return 4 - (i - 1); //so instead spawn the previous one
        }
    }

    //Takes an int between 0 and 100
    public int getValueMetaToSpawn(World world, int randIn) {
        return EnumOreValue.byWorldValue(world, getValueToSpawn(randIn)).getMetadata();
    }

    //Takes an int between 0 and 100
    public int getValueMetaToSpawn(int world, int randIn) {
        return EnumOreValue.byWorldValue(world, getValueToSpawn(randIn)).getMetadata();
    }

    //Takes an int between 0 and 100
    public int getValueMetaToSpawn(World world) {
        return EnumOreValue.byWorld(world).getMetadata();
    }

    //Takes an int between 0 and 100
    public int getValueMetaToSpawn(int world) {
        return EnumOreValue.byWorld(world).getMetadata();
    }
}
