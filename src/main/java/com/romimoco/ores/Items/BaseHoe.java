package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseHoe extends ItemHoe implements IHasCustomModel, IColoredItem{

    private int color = 0x000000;

    public BaseHoe(ToolMaterial t, int color) {
        super(t);
        this.color = color;

        this.setUnlocalizedName(Ores.MODID + ".hoe"+t.name());
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("Minecraft:iron_hoe"));
    }

    public int getColor(){
        return this.color;
    }
}
