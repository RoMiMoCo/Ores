package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseSword extends ItemSword implements IHasCustomModel, IColoredItem {

    private int color = 0x000000;

    public BaseSword(ToolMaterial t, int color) {
        super(t);
        this.color = color;
        this.setUnlocalizedName(Ores.MODID + ":sword" + t.name());

        OreLogger.localize(this.getUnlocalizedName() + ".name=" + t.name().substring(0, 1).toUpperCase() + t.name().substring(1) + " Sword");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("Minecraft:iron_sword"));
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
