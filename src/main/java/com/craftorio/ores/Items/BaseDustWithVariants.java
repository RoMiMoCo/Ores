package com.craftorio.ores.Items;

import com.craftorio.ores.Ores;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.enums.EnumOreValue;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.craftorio.ores.util.OreLogger;
import net.minecraft.block.properties.PropertyEnum;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseDustWithVariants extends BaseDust implements IHasCustomModel, IColoredItem {


    public static final PropertyEnum PROPERTYOREVALUE = PropertyEnum.create("orevalue", EnumOreValue.class);

    public BaseDustWithVariants(BaseOre b) {
        super(b);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MISC);

        for (EnumOreValue v : EnumOreValue.overworldOres) {
            OreLogger.localize(this.getTranslationKey(new ItemStack(this, 1, v.getMetadata())) + ".name=" + EnumOreValue.dustNameByMetadata(v.getMetadata()).substring(0, 1).toUpperCase() +
                    EnumOreValue.dustNameByMetadata(v.getMetadata()).substring(1) + " pile of " + b.name.substring(0, 1).toUpperCase() + b.name.substring(1) + " Dust");
        }
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (EnumOreValue v : EnumOreValue.overworldOres) {
            ModelLoader.setCustomModelResourceLocation(this, v.getMetadata(), new ModelResourceLocation(Ores.NAME + ":basedust_" + v));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(itemIn)) {
            for (EnumOreValue value : EnumOreValue.overworldOres) {
                list.add(new ItemStack(this, 1, value.getMetadata()));
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey().replaceFirst("dust", EnumOreValue.dustNameByMetadata(stack.getMetadata())) + "dust";
    }
}
