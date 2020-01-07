package com.craftorio.ores.integrations;

import com.craftorio.ores.world.OreGenDimDefinition;
import jeresources.api.distributions.DistributionSquare;

public class JEROreDistribution extends DistributionSquare {
    public JEROreDistribution(OreGenDimDefinition def) {
        super(
                def.minY,
                def.maxY,
                (float)def.spawnChance/256
        );
    }
}
