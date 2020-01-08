package com.craftorio.ores.integrations;

import com.craftorio.ores.Items.BaseCrushed;
import com.craftorio.ores.Items.ModItems;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.enums.EnumOreValue;
import ic2.api.recipe.Recipes;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

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
            addMacerateReceipt(ore);
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

    private void addCrushed(BaseOre ore) {
        BaseCrushed crushedOre = new BaseCrushed(ore);
        ModItems.MISC.put(ore.name + "Crushed", crushedOre);
    }

    private void addMacerateReceipt(BaseOre ore) {
            if (ModItems.MISC.containsKey(ore.name + "Crushed")) {
                for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
                    BaseCrushed crushedOre = (BaseCrushed) ModItems.MISC.get(ore.name + "Crushed");
                    addMacerateReceipt(
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
                registerCrushedItem(event, (BaseCrushed)item);
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
