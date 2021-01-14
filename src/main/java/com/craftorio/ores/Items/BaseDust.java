package com.craftorio.ores.Items;

import com.craftorio.ores.Ores;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.enums.EnumOreValue;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseDust extends Item implements IColoredItem, IHasCustomModel {

    private int color;
    public String name;
    private BaseOre ore;

    public BaseDust(BaseOre b) {
        super();
        this.ore = b;
        this.name = b.name;
        this.color = b.getColor();
        this.setTranslationKey(Ores.MODID + ":dust" + b.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "dust" + name);

        if (ore.genVariants) {
            this.setHasSubtypes(true);
            for (EnumOreValue v : EnumOreValue.overworldOres) {
                OreLogger.localize(this.getTranslationKey(new ItemStack(this, 1, v.getMetadata())) + ".name=" + EnumOreValue.dustNameByMetadata(v.getVariant()).substring(0, 1).toUpperCase() +
                        EnumOreValue.dustNameByMetadata(v.getVariant()).substring(1) + " pile of " + b.name.substring(0, 1).toUpperCase() + b.name.substring(1) + " Dust");
            }
        } else {
                OreLogger.localize(this.getTranslationKey() + ".name=" + b.name.substring(0, 1).toUpperCase() + b.name.substring(1) + " Dust");
        }
    }

    public BaseOre getOre()
    {
        return ore;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        if (ore.genVariants) {
            for (EnumOreValue v : EnumOreValue.overworldOres) {
                ModelLoader.setCustomModelResourceLocation(this, v.getMetadata(), new ModelResourceLocation(Ores.NAME + ":basedust_" + v));
            }
        } else {
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME + ":basedust"));
        }
    }

    public int getColor() {
        return color;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (OreConfig.requireResourcePack) {
            return super.getItemStackDisplayName(stack);
        }
        return Ores.proxy.langs.translate(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> list) {
        if (ore.genVariants) {
            if (this.isInCreativeTab(itemIn)) {
                for (EnumOreValue value : EnumOreValue.overworldOres) {
                    list.add(new ItemStack(this, 1, value.getMetadata()));
                }
            }
        } else {
            super.getSubItems(itemIn, list);
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if (ore.genVariants) {
            return super.getTranslationKey().replaceFirst("dust", EnumOreValue.dustNameByMetadata(stack.getMetadata())) + "dust";
        }

        return super.getTranslationKey(stack);
    }
}
