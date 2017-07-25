package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BasePickaxe extends ItemPickaxe implements IHasCustomModel,IColoredItem{

    private int color = 0x000000;

    public BasePickaxe(ToolMaterial t, int color) {
        super(t);
        this.color = color;
        this.setUnlocalizedName(Ores.MODID + ".pickaxe"+t.name());
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("Minecraft:iron_pickaxe"));
    }

    public int getColor(){
        return this.color;
    }
}
