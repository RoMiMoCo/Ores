package com.romimoco.ores.blocks;

import com.google.gson.JsonObject;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BaseGem extends BaseOre implements IColoredItem, IHasCustomModel {

    public String name;
    private String drop;

    public BaseGem(JsonObject gemDefinition) {
        super(gemDefinition);

        float hardness=4.5f;
        String drop="minecraft:coal";


        try{
            hardness = gemDefinition.get("Hardness").getAsFloat();
        }catch(Exception e){}

        try{
            drop = gemDefinition.get("Drops").getAsString();
        }catch(Exception e){}

        this.drop = drop;
        this.setHardness(hardness);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        //TODO: extend this to custom gems
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(drop));
    }
}
