package com.craftorio.ores.Items;

import com.craftorio.ores.Ores;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.craftorio.ores.util.OreConfig;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseShears extends ItemShears implements IHasCustomModel, IColoredItem {

    private int color = 0x000000;

    public BaseShears(ToolMaterial t, int color) {

        super();
        this.color = color;
        this.setTranslationKey(Ores.MODID + ":shears" + t.name());

        OreLogger.localize(this.getTranslationKey() + ".name=" + t.name().substring(0, 1).toUpperCase() + t.name().substring(1) + " Shears");

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("Minecraft:shears"));
    }

    public int getColor() {
        return this.color;
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
