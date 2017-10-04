package com.romimoco.ores.Items;

import com.google.gson.JsonObject;
import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseGem;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;


public class BaseGemDrop extends Item implements IColoredItem, IHasCustomModel {

    private int color;
    public String name;
    private String cut;

    public BaseGemDrop(JsonObject definition, BaseGem b) {

        super();
        this.name = b.name;
        this.color = b.getColor();
        this.setUnlocalizedName(Ores.MODID + ":gem" + b.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "gem"+name);

        this.cut = "teardrop";
        try{
            this.cut = definition.get("Cut").getAsString();
        }catch(Exception e){}




        OreLogger.localize(this.getUnlocalizedName() + ".name=" + b.name.substring(0,1).toUpperCase() + b.name.substring(1));
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME +":gem_" + this.cut));
    }
}
