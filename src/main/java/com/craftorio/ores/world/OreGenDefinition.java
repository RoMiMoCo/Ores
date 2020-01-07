package com.craftorio.ores.world;

import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.util.OreLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;

public class OreGenDefinition {
    public HashMap<Integer, OreGenDimDefinition> oreGenDimDefinitions = new HashMap<>();

    public OreGenDefinition(BaseOre ore, JsonObject def) {
        if (def == null) {
            return; //no ore gen specified
        }
        try {
            JsonArray dimensions = def.getAsJsonArray("Dimensions");
            for (JsonElement d : dimensions) {
                addDimDef(new OreGenDimDefinition(ore, d.getAsJsonObject()));
            }
        } catch (Exception e) {
            OreLogger.debug(ore.name + ": Dimensions config wasn't provided, fallback to legacy");
        }

        // fallback to old config
        if (0 == this.oreGenDimDefinitions.size()) {
            addDimDef(new OreGenDimDefinition(ore, def));
        }
    }

    private void addDimDef(OreGenDimDefinition dimDefinition)
    {
        if (!this.oreGenDimDefinitions.containsKey(dimDefinition.dimId)) {
            this.oreGenDimDefinitions.put(dimDefinition.dimId, dimDefinition);
            OreLogger.debug(dimDefinition.ore.name
                    + " was registered for DIM: " + dimDefinition.dimId
                    + " like DIM: " + dimDefinition.dimLike
                    + "; minY: " + dimDefinition.minY
                    + "; maxY: " + dimDefinition.maxY
                    + "; spawnChance: " + dimDefinition.spawnChance
                    + "; veinSize: " + dimDefinition.veinSize
            );
        }
    }
}
