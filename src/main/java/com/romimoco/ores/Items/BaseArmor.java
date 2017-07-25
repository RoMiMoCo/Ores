package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class BaseArmor extends ItemArmor implements IColoredItem{

    private int color = 0x000000;
    public BaseArmor(ArmorMaterial mat, int renderIndex, EntityEquipmentSlot slot, int color){
        super(mat, renderIndex, slot);
        this.setUnlocalizedName(Ores.MODID + "." +mat.name() + getArmorPieceName());
        this.color = color;
    }


    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Ores.NAME + ":base_"+getArmorPieceName()));
    }

    private String getArmorPieceName(){


        switch(this.getEquipmentSlot()){
            case FEET: return "boots";
            case LEGS: return "leggings";
            case CHEST: return "chestplate";
            case HEAD: return "helmet";
            default: return "";
        }

    }

    @Override
    public int getColor(ItemStack stack){
        return this.color;
    }

    @Override
    public int getColor() {
        return getColor(ItemStack.EMPTY);
    }
}
