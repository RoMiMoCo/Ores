package com.craftorio.ores.crafting;

import com.craftorio.ores.Items.BaseIngotWithVariants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreMetadataRecipe extends ShapedOreRecipe {


    public ShapedOreMetadataRecipe(ResourceLocation group, ItemStack result, Object... recipe) {
        super(group, result, recipe);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        ItemStack res = super.getCraftingResult(var1);

        int count = 0;
        float meta = 0;

        for (int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack itemstack = var1.getStackInSlot(i);
            if (itemstack.isEmpty()) {
                continue;
            }
            if (itemstack.getItem() instanceof BaseIngotWithVariants) {
                count++;
                meta += 5 - itemstack.getMetadata();
            } else {
                boolean stick = false;
                for (int id : OreDictionary.getOreIDs(itemstack)) {
                    if (OreDictionary.getOreName(id) == "stickWood" || OreDictionary.getOreName(id) == "plankWood") {
                        stick = true;
                        break;
                    }
                }
                if (!stick) { //make sure it's not a stick
                    count++;
                    meta += 5; //if not one of my ingots, assume full ingot
                }
            }
        }
        int maxDmg = res.getMaxDamage();
        meta = meta / (5 * count); //percentage of the total durability that should be kept
        res.setItemDamage(maxDmg - (int) (maxDmg * meta));
        return res;
    }

    @Override
    protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
                int subX = x - startX;
                int subY = y - startY;
                Ingredient target = Ingredient.EMPTY;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirror) {
                        target = input.get(width - subX - 1 + subY * width);
                    } else {
                        target = input.get(subX + subY * width);
                    }
                }

                if (!target.apply(inv.getStackInRowAndColumn(x, y))) {
                    return false;
                }
            }
        }

        return true;
    }
}
