package com.romimoco.ores.Items;

import com.google.gson.JsonObject;
import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseGemOre;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;


public class BaseGem extends Item implements IColoredItem, IHasCustomModel {

    private int color;
    public String name;
    private String cut;
    private int burnTime;
    public String type;

    public BaseGem(JsonObject definition, BaseGemOre b) {

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

        this.burnTime = 0;
        try{
            this.burnTime = definition.get("BurnTime").getAsInt();
        }catch(Exception e){}



        OreLogger.localize(this.getUnlocalizedName() + ".name=" + b.name.substring(0,1).toUpperCase() + b.name.substring(1));
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void initModel() {
        if(this.cut.equals("dust")){
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME + ":basedust_rich"));
        }else{
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME +":gem_" + this.cut));
        }
    }

    @Override
    public int getItemBurnTime(ItemStack stack){
       return this.burnTime;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(OreConfig.requireResourcePack) {
            return super.getItemStackDisplayName(stack);
        }
        return Ores.proxy.langs.translate(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
}
