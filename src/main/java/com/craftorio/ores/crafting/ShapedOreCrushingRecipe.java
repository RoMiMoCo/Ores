package com.craftorio.ores.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreCrushingRecipe extends ShapedOreRecipe {


    public ShapedOreCrushingRecipe(ResourceLocation group, ItemStack result, Object... recipe) {
        super(group, result, recipe);
    }


    @Override
    public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inventoryCrafting.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < remainingItems.size(); ++i) {
            final ItemStack itemstack = inventoryCrafting.getStackInSlot(i);

            if (!itemstack.isEmpty() && !(itemstack.getItem().getTranslationKey().contains("ore"))) {
                remainingItems.set(i, itemstack.copy());
            } else {
                remainingItems.set(i, ForgeHooks.getContainerItem(itemstack));
            }
        }

        return remainingItems;
    }
}
