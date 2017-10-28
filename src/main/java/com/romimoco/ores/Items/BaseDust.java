package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseDust extends Item implements IColoredItem, IHasCustomModel{

    private int color;
    public String name;

    public BaseDust(BaseOre b){
       super();
       this.name = b.name;
       this.color = b.getColor();
       this.setUnlocalizedName(Ores.MODID + ":dust" + b.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "dust"+name);

        OreLogger.localize(this.getUnlocalizedName() + ".name=" + b.name.substring(0,1).toUpperCase() + b.name.substring(1) + " Dust");

    }


    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME +":basedust_rich"));
    }

    public int getColor() {
        return color;
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
