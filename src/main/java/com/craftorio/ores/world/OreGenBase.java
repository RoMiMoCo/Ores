package com.craftorio.ores.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.LinkedList;
import java.util.Random;

public class OreGenBase implements IWorldGenerator {
    private LinkedList<OreGenDefinition> ores = new LinkedList<>();

    public void add(OreGenDefinition def) {
        ores.add(def);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        for (OreGenDefinition def : ores) {
            OreGenDimDefinition dimDef = def.oreGenDimDefinitions.get(world.provider.getDimensionType().getId());
            String currentBiome = world.getBiomeForCoordsBody(new BlockPos((chunkX * 16) + 8, 64, (chunkZ * 16) + 8)).getRegistryName().getPath();
            if (dimDef.biomes == null || dimDef.biomes.contains(currentBiome)) { //if no biomes specified for spawn, or the current biome is specified
                addOreSpawn(world, random, chunkX * 16, chunkZ * 16, 16, 16, def);
            }
        }
    }

    private void addOreSpawn(World world, Random random, int xPos, int zPos, int maxX, int maxZ, OreGenDefinition def) {
        int dimId = world.provider.getDimensionType().getId();
        if (def.oreGenDimDefinitions.containsKey(dimId)) {
            OreGenDimDefinition dimDef = def.oreGenDimDefinitions.get(dimId);
            for (int i = dimDef.spawnChance; i-- > 0; ) {
                int x = xPos + random.nextInt(maxX);
                int yRange = dimDef.maxY - dimDef.minY;
                int y = dimDef.minY + random.nextInt((yRange > 0) ? yRange : 1);
                int z = zPos + random.nextInt(maxZ);

                BlockMatcher blockMatcher;
                if (dimDef.dimLike == DimensionType.NETHER.getId()) {
                    blockMatcher = BlockMatcher.forBlock(Blocks.NETHERRACK);
                } else if (dimDef.dimLike == DimensionType.THE_END.getId()) {
                    blockMatcher = BlockMatcher.forBlock(Blocks.END_STONE);
                } else {
                    blockMatcher = BlockMatcher.forBlock(Blocks.STONE);
                }

                IBlockState state;
                if (dimDef.ore.genVariants) {
                    state = dimDef.ore.getStateFromMeta(dimDef.getValueMetaToSpawn(dimId, random.nextInt(100)));
                } else {
                    state = dimDef.ore.getStateFromMeta(dimDef.getValueMetaToSpawn(dimId));
                }

                (new WorldGenMinable(state, dimDef.veinSize, blockMatcher)).generate(world, random, new BlockPos(x, y, z));
            }
        }
    }
}
