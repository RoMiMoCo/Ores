package com.craftorio.ores.integrations;

import com.craftorio.ores.Items.BaseCrushed;
import com.craftorio.ores.Items.ModItems;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.crafting.RecipeManager;
import com.craftorio.ores.enums.EnumOreValue;
import ic2.api.recipe.Recipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;

public class IC2Integration implements IOreIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for (BaseOre ore : ModBlocks.ORES) {
            addCrushed(ore);
        }
    }

    @Override
    public void Init(FMLInitializationEvent event) {
        for (BaseOre ore : ModBlocks.ORES) {
            registerMacerateReceipt(ore);
            registerVariantCombinationRecipes(ore);
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }


    protected void registerMacerateReceipt(ItemStack input, ItemStack output) {
        Recipes.macerator.addRecipe(new IC2RecipeInput(input), null, false, output);
    }

    private void addCrushed(BaseOre ore) {
        BaseCrushed crushedOre = new BaseCrushed(ore);
        ModItems.MISC.put(ore.name + "Crushed", crushedOre);
    }

    private void registerVariantCombinationRecipes(BaseOre ore) {
        if (ore.genVariants) {
            if (ModItems.MISC.containsKey(ore.name + "Crushed")) {
                String resourcePathBase = "recipe" + ore.name;
                Object[] size2 = new Object[2];
                Object[] size4 = new Object[4];
                Object[] size8 = new Object[8];
                BaseCrushed crushedOre = (BaseCrushed) ModItems.MISC.get(ore.name + "Crushed");
                for (EnumOreValue value : EnumOreValue.oreValues(ore, DimensionType.OVERWORLD.getId())) {
                    for (int i = 1 + value.getVariant(); i <= EnumOreValue.VARIANT_POOR; i++) {
                        ItemStack output = new ItemStack(crushedOre, 1, value.getMetadata());
                        if (i - value.getVariant() == 1) {
                            Arrays.fill(size2, Ingredient.fromStacks(new ItemStack(crushedOre, 1, i)));
                            RecipeManager.registerShapelessRecipe(
                                    resourcePathBase
                                            + EnumOreValue.byWorldValue(DimensionType.OVERWORLD.getId(), i).name()
                                            + "To"
                                            + value.name()
                                            + "Crushed",
                                    output,
                                    size2
                            );
                        } else if (i - value.getVariant() == 2) {
                            Arrays.fill(size4, Ingredient.fromStacks(new ItemStack(crushedOre, 1, i)));
                            RecipeManager.registerShapelessRecipe(
                                    resourcePathBase
                                            + EnumOreValue.byWorldValue(DimensionType.OVERWORLD.getId(), i).name()
                                            + "To"
                                            + value.name()
                                            + "Crushed",
                                    output,
                                    size4
                            );
                        } else if (i - value.getVariant() == 3) {
                            Arrays.fill(size8, Ingredient.fromStacks(new ItemStack(crushedOre, 1, i)));
                            RecipeManager.registerShapelessRecipe(resourcePathBase
                                            + EnumOreValue.byWorldValue(DimensionType.OVERWORLD.getId(), i).name()
                                            + "To"
                                            + value.name()
                                            + "Crushed",
                                    output,
                                    size8
                            );
                        }
                    }
                }
            }
        }
    }

    private void registerMacerateReceipt(BaseOre ore) {
        if (ModItems.MISC.containsKey(ore.name + "Crushed")) {
            for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
                BaseCrushed crushedOre = (BaseCrushed) ModItems.MISC.get(ore.name + "Crushed");
                registerMacerateReceipt(
                        new ItemStack(ore, 1, value.getMetadata()),
                        new ItemStack(crushedOre, 2, value.getVariant())
                );
                if (ore.genIngots) {
                    GameRegistry.addSmelting(
                            new ItemStack(crushedOre, 1, value.getVariant()),
                            new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, value.getVariant()),
                            0.7f - ((float) value.getVariant() / 10)
                    );
                }
            }
        }
    }

    public void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.MISC.values()) {
            if (item instanceof BaseCrushed) {
                registerCrushedItem(event, (BaseCrushed) item);
            }
        }
    }

    public void registerCrushedItem(RegistryEvent.Register<Item> event, BaseCrushed crushedOre) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(crushedOre);
        for (EnumOreValue value : EnumOreValue.oreValues(crushedOre.getOre())) {
            if (value.getVariant() > 0) {
                OreDictionary.registerOre("crushed" + crushedOre.getOre().name + value, new ItemStack(crushedOre, 1, value.getVariant()));
            } else {
                OreDictionary.registerOre("crushed" + crushedOre.getOre().name, new ItemStack(crushedOre, 1, value.getVariant()));
            }
        }
    }
}
