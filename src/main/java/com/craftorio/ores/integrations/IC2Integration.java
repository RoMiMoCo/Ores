package com.craftorio.ores.integrations;

import com.craftorio.ores.Items.ModItems;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.enums.EnumOreValue;
import ic2.api.recipe.Recipes;
import net.minecraft.block.BlockOre;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class IC2Integration implements IOreIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {


    }

    @Override
    public void Init(FMLInitializationEvent event) {
        for (BaseOre ore : ModBlocks.ORES) {
            if (ore.genDusts) {
                addMacerateReceipt(ore);
            }
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    protected void addMacerateReceipt(BlockOre input, ItemStack output) {
        addMacerateReceipt(new ItemStack(input), output);
    }

    protected void addMacerateReceipt(ItemStack input, ItemStack output) {
        Recipes.macerator.addRecipe(new IC2RecipeInput(input), null, false, output);
    }

    private void addMacerateReceipt(BaseOre ore) {
        for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
            if (ore.shouldRegister) {
                addMacerateReceipt(
                    new ItemStack(ore, 1, value.getMetadata()),
                    new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 2, value.getVariant())
                );
            }
            addMacerateReceipt(
                new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, value.getVariant()),
                new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, value.getVariant())
            );
        }
    }
}
