package com.craftorio.ores.integrations;

import com.craftorio.ores.blocks.BaseGemOre;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.enums.EnumOreValue;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.world.OreGenDimDefinition;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class JERIntegration implements IOreIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void Init(FMLInitializationEvent event) {

    }

    private void registerWorldGen(BaseOre ore)
    {
        for (OreGenDimDefinition dimDef: ore.oreGenDefinition.oreGenDimDefinitions.values()) {
            for (EnumOreValue value : EnumOreValue.oreValues(ore, dimDef.dimLike)) {
                if (value.getDimId() == dimDef.dimLike) {
                    LootDrop drop;
                    if (ore instanceof BaseGemOre) {
                        BaseGemOre gemOre = (BaseGemOre)ore;
                        drop = new LootDrop(new ItemStack(gemOre.getItemDropped(), 1, gemOre.getItemMetaDropped()));
                        drop.minDrop = 1;
                        drop.minDrop = 4;
                    } else {
                        drop = new LootDrop(new ItemStack(ore, 1, value.getMetadata()));
                    }
                    if (DimensionManager.isDimensionRegistered(dimDef.dimId)) {
                        JERAPI.getInstance().getWorldGenRegistry().register(
                                new ItemStack(ore, 1, value.getMetadata()),
                                new JEROreDistribution(dimDef),
                                new Restriction(new DimensionRestriction(
                                        DimensionManager.getProviderType(dimDef.dimId)
                                )),
                                drop
                        );
                        OreLogger.debug("Registering JER WorldGenEntry for: "
                                + ore.toString()
                                + ":"
                                + value.getMetadata()
                                + "; variant: " + value.getVariant()
                                + "; dimId: " + dimDef.dimId
                                + "; minY: " + dimDef.minY
                                + "; maxY: " + dimDef.maxY
                                + "; chance: " + dimDef.spawnChance
                        );
                    } else {
                        OreLogger.debug("Dimension " + dimDef.dimId + " wasn't registered");
                    }
                }
            }
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {
        for (BaseOre ore : ModBlocks.ORES) {
            registerWorldGen(ore);
        }
        for (BaseGemOre ore : ModBlocks.GEMS.values()) {
            registerWorldGen(ore);
        }
    }

    public void registerItems(RegistryEvent.Register<Item> event)
    {

    }
}
