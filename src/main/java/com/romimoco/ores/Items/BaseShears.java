package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseShears extends ItemShears implements IHasCustomModel,IColoredItem {

    private int color = 0x000000;

    public BaseShears(ToolMaterial t, int color) {

        super();
        this.color = color;
        this.setUnlocalizedName(Ores.MODID + ":shears"+t.name());

        OreLogger.localize(this.getUnlocalizedName() + ".name=" + t.name().substring(0,1).toUpperCase() + t.name().substring(1) + " Shears");

    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("Minecraft:shears"));
    }

    public int getColor(){
        return this.color;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(OreConfig.requireResourcePack) {
            return super.getItemStackDisplayName(stack);
        }
        return Ores.proxy.langs.translate(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
}
