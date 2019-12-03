package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseShield extends ItemShield implements IHasCustomModel, IColoredItem {

    private int color;

    public BaseShield(BaseOre b) {
        super();
        this.color = b.getColor();
        this.setUnlocalizedName(Ores.MODID + ":shield" + b.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "shield" + b.name);
        this.setMaxDamage((int) (b.getHardness() / 3.0f * 336));
        OreLogger.localize(this.getUnlocalizedName() + ".name=" + StringUtil.toSentenceCase(b.name) + " Shield");
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public int getColor() {
        return 0;
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
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME + ":base_shield"));
    }

    @Override
    public boolean isShield(ItemStack stack, EntityLivingBase entity) {
        return true;
    }
}
