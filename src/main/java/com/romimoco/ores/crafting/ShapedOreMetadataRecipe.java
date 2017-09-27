package com.romimoco.ores.crafting;

import com.romimoco.ores.Items.BaseIngot;
import com.romimoco.ores.Items.BaseIngotWithVariants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreMetadataRecipe extends ShapedOreRecipe {


    public ShapedOreMetadataRecipe(ResourceLocation group, ItemStack result, Object... recipe) {
        super(group, result, recipe);
    }

    public boolean matches(InventoryCrafting inv, World world){
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {


            }

        }
        return false;
    }

    @Override
    public ItemStack  getCraftingResult(InventoryCrafting var1){
        ItemStack res = super.getCraftingResult(var1);

        int count = 0;
        float meta = 0;

        for(int i = 0; i < var1.getSizeInventory(); i++){
            ItemStack itemstack = var1.getStackInSlot(i);
            if(itemstack.isEmpty()){
                continue;
            }
            count++;
            if(itemstack.getItem() instanceof BaseIngotWithVariants){
               meta += 5 - itemstack.getMetadata();
            }else{
                meta += 5; //if not one of my ingots, assume full ingot
            }
       }
       int maxDmg = res.getMaxDamage();

       meta = meta / 5 * count; //percentage of the total durability that should be kept
       res.setItemDamage((int)(maxDmg * meta));

       return res;
   }

}
