package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseIngot extends Item implements IColoredItem, IHasCustomModel{

    private int color;
    public String name;

    public BaseIngot(BaseOre b){
       super();
       this.name = b.name;
       this.color = b.getColor();
       this.setUnlocalizedName(Ores.MODID + ".ingot" + b.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "ingot"+name);

    }


    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("Minecraft:iron_ingot"));
    }

    public int getColor() {
        return color;
    }
}
