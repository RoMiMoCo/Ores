package com.craftorio.ores.integrations;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.IERecipes;
import com.craftorio.ores.Items.ModItems;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.enums.EnumOreValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class IEIntegration implements IOreIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {


    }

    @Override
    public void Init(FMLInitializationEvent event) {
        for (BaseOre ore : ModBlocks.ORES) {
            if (ore.genDusts) {
                addCrushing(ore);
            }
            addArcSmelting(ore);
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void addCrushing(BaseOre ore) {
        for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
            int variant = value.getVariant();
            if (ore.shouldRegister) {
                IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 2, variant), new ItemStack(ore, 1, value.getMetadata()), 6000);
            }
            int energy = (int) (variant > 0 ? Math.pow(2, 4 - variant) * 225 : 3600);
            IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, variant), new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, variant), energy);
        }
    }

    public void addArcSmelting(BaseOre ore) {

        if (ore.shouldRegister) {
            if (ore.genVariants) {
                for (int i = 0; i < 5; i++) {
                    if (i == 0) {
                        IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 2, i), ore, 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                        if (ore.genDusts) {
                            IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, i), new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, 0), 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                        }
                    } else {
                        IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, i - 1), new ItemStack(ore, 1, i), 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                        if (ore.genDusts) {
                            IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, i), new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, i), (int) Math.pow(2, 4 - i) * 12, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                        }
                    }
                }
            } else { //just register  for the meta 0's
                IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 2, 0), ore, 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                if (ore.genDusts) {
                    IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, 0), new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, 0), 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                }
            }
        } else { //Even if we don't register the ore, we still need to add smelting for the  dusts
            if (!ore.genDusts) {
                return;
            }
            if (ore.genVariants) {
                for (int i = 0; i < 5; i++) {
                    IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, i), new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, i), (int) Math.pow(2, 4 - i) * 12, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                }
            } else {
                IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, 0), new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, 0), 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
            }
        }
    }

    public void registerItems(RegistryEvent.Register<Item> event)
    {

    }
}
