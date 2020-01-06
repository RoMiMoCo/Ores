package com.craftorio.ores.Items;

import com.craftorio.ores.Ores;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

        OreLogger.localize(this.getTranslationKey() + ".name=" + b.name.substring(0, 1).toUpperCase() + b.name.substring(1) + " Dust");

    }

    public BaseOre getOre()
    {
        return ore;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME + ":basedust_rich"));
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
}
