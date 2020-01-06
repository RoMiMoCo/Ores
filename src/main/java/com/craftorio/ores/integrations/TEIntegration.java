package com.craftorio.ores.integrations;

import cofh.thermalexpansion.util.managers.machine.CompactorManager;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import com.craftorio.ores.Items.BaseDust;
import com.craftorio.ores.Items.BaseIngot;
import com.craftorio.ores.Items.ModItems;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.enums.EnumOreValue;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TEIntegration implements IOreIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void Init(FMLInitializationEvent event) {
        for (BaseOre ore : ModBlocks.ORES) {
            if (ore.genDusts) {
                integratePulverizer(ore);
            }

            if (FluidRegistry.isFluidRegistered(ore.name)) {
                integrateMagmaCrucible(ore, FluidRegistry.getFluid(ore.name));
            }

            if (ore.genVariants) {
                integrateCompactor(ore);
            }
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void integratePulverizer(BaseOre ore) {
        //add grinding to dusts if ores and dusts are enabled
        BaseDust Dust = ModItems.DUSTS.get(ore.name + "Dust");
        BaseIngot Ingot = ModItems.INGOTS.get(ore.name + "Ingot");
        for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
            PulverizerManager.addRecipe(PulverizerManager.DEFAULT_ENERGY, new ItemStack(ore, 1, value.getMetadata()), new ItemStack(Dust, 2, value.getVariant()));
            PulverizerManager.addRecipe(PulverizerManager.DEFAULT_ENERGY / 2, new ItemStack(Ingot, 1, value.getVariant()), new ItemStack(Dust, 2, value.getVariant()));
        }
    }

    private void integrateMagmaCrucible(BaseOre ore, Fluid f) {
        //if fluid exists add melting
        if (ore.genVariants) {
            for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
                CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY, new ItemStack(ore, 1, value.getMetadata()), new FluidStack(f, 288 / ((int) Math.pow(2, value.getVariant()))));
                CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY / (int) Math.pow(2, value.getVariant()), new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, value.getVariant()), new FluidStack(f, 144 / ((int) Math.pow(2, value.getVariant()))));
            }
        } else {
            CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY, new ItemStack(ore, 1, 0), new FluidStack(f, 288));
            CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY, new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, 0), new FluidStack(f, 144));
        }
    }

    private void integrateCompactor(BaseOre ore) {
        //compactor storage recipes for variant ingots
        BaseIngot ingot = ModItems.INGOTS.get(ore.name + "Ingot");
        for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
            CompactorManager.addRecipe((int) (CompactorManager.DEFAULT_ENERGY / Math.pow(2, value.getVariant())), new ItemStack(ingot, 2, value.getVariant()+1), new ItemStack(ingot, 1, value.getVariant()), CompactorManager.Mode.ALL);
        }
    }
}
