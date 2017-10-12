package com.romimoco.ores.blocks;

import com.google.gson.JsonObject;
import com.romimoco.ores.Items.BaseGem;
import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.google.gson.JsonParser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BaseGemOre extends BaseOre implements IColoredItem, IHasCustomModel {

    private String drop;

    public BaseGemOre(JsonObject gemDefinition) {
        super(gemDefinition);

        float hardness=4.5f;
        String drop="minecraft:coal";


        try{
            hardness = gemDefinition.get("Hardness").getAsFloat();
        }catch(Exception e){}

        try{
            drop = gemDefinition.get("Drops").toString();
        }catch(Exception e){
        }

        if(drop.charAt(0) != '{'){
            drop = drop.substring(1, drop.length()-1); //trim the excess quotes off
            this.drop = drop;
        }else{
            JsonParser parser = new JsonParser();
            JsonObject def = (JsonObject) parser.parse(drop);
            BaseGem gem = new BaseGem(def, this);

            this.drop = gem.getUnlocalizedName().replaceFirst("item.","");
            String type = def.get("Type").getAsString();

            if(type == null || !type.equals("gem")){
                ModItems.MISC.put(this.name + "Gem" , gem);
            }else{
                ModItems.GEMS.put(this.name + "Gem" , gem);
            }
        }
        this.setHardness(hardness);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(drop));
    }
}
