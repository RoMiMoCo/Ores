package com.craftorio.ores.integrations;

import ic2.api.recipe.IRecipeInput;
import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.List;

public class IC2RecipeInput implements IRecipeInput {
    private final ItemStack input;

    public IC2RecipeInput(ItemStack input){
        this.input = input;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        return itemStack != null && input.isItemEqual(itemStack) && input.getItemDamage() == itemStack.getItemDamage();
    }

    @Override
    public int getAmount() {
        return input.getCount();
    }

    @Override
    public List<ItemStack> getInputs() {
        return Collections.singletonList(input);
    }
}
