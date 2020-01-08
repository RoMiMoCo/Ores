package com.craftorio.ores.Items;

import com.craftorio.ores.Ores;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import com.craftorio.ores.enums.EnumOreValue;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseCrushed extends Item implements IColoredItem, IHasCustomModel {

    private int color;
    public String name;
    private BaseOre ore;

    public BaseCrushed(BaseOre b) {
        super();
        this.ore = b;
        this.name = b.name;
        this.color = b.getColor();
        this.setTranslationKey(Ores.MODID + ":crushed" + this.ore.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "crushed" + this.ore.name);

        if (ore.genVariants) {
            this.setHasSubtypes(true);
        }

        if (ore.genVariants) {
            for (EnumOreValue v : EnumOreValue.overworldOres) {
//                String variantName = EnumOreValue.oreNameByMetadata(v.getVariant());
//                String variant = variantName.substring(0, 1).toUpperCase() + variantName.substring(1);
//                String name = "Crushed " + variant + " " + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);
//                String variant = variantName.substring(0, 1).toUpperCase() + variantName.substring(1);
                String name = "Crushed " + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);
                OreLogger.localize(this.getTranslationKey(new ItemStack(this, 1, v.getVariant())) + ".name=" + name);
            }
        } else {
            String name = "Crushed " + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);
            OreLogger.localize(this.getTranslationKey() + ".name=" + name);
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
                ModelLoader.setCustomModelResourceLocation(this, v.getMetadata(), new ModelResourceLocation(Ores.NAME + ":crushed_" + EnumOreValue.oreNameByMetadata(v.getVariant())));
            }
        } else {
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME + ":crushed_" + EnumOreValue.oreNameByMetadata(EnumOreValue.VARIANT_RICH)));
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
            return super.getTranslationKey().replaceFirst("crushed", EnumOreValue.oreNameByMetadata(stack.getMetadata())) + "crushed";
        }

        return super.getTranslationKey(stack);
    }
}
