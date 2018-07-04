package com.romimoco.ores.integrations;

import cofh.thermalexpansion.util.managers.machine.CompactorManager;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import com.romimoco.ores.Items.BaseDust;
import com.romimoco.ores.Items.BaseIngot;
import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.block.Block;
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
        for(Block b: ModBlocks.ORES) {

            if(OreConfig.genDusts){
                integratePulverizer((BaseOre)b);
            }

            if(FluidRegistry.isFluidRegistered(((BaseOre)b).name)){
                integrateMagmaCrucible(((BaseOre)b), FluidRegistry.getFluid(((BaseOre)b).name));
            }

            if(OreConfig.genVariants){
                integrateCompactor((BaseOre)b);
            }
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void integratePulverizer(BaseOre b) {
        //add grinding to dusts if ores and dusts are enabled
        BaseDust Dust = (BaseDust)ModItems.DUSTS.get(b.name + "Dust");
        BaseIngot Ingot = (BaseIngot)ModItems.INGOTS.get(b.name + "Ingot");

        if(OreConfig.genVariants){
            for(int i = 0; i < 5; i ++){
                PulverizerManager.addRecipe(PulverizerManager.DEFAULT_ENERGY, new ItemStack(b, 1, i), new ItemStack(Dust, i==0?2:1, i==0?i:i-1));
                PulverizerManager.addRecipe((PulverizerManager.DEFAULT_ENERGY/2)/(int)Math.pow(2,i), new ItemStack(Ingot, 1, i), new ItemStack(Dust, 1, i));
            }
        }else{
            PulverizerManager.addRecipe(PulverizerManager.DEFAULT_ENERGY, new ItemStack(b, 1, 0), new ItemStack(Dust, 2, 0));
            PulverizerManager.addRecipe(PulverizerManager.DEFAULT_ENERGY/2, new ItemStack(Ingot, 1, 0), new ItemStack(Dust, 2, 0));
        }
    }

    private void integrateMagmaCrucible(BaseOre b, Fluid f){
        //if fluid exists add melting
        if(OreConfig.genVariants) {
            for(int i = 0; i < 5; i ++) {
                CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY, new ItemStack(b, 1, i), new FluidStack(f, 288 / ((int)Math.pow(2,i))));
                CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY / (int)Math.pow(2, i), new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i), new FluidStack(f, 144 / ((int)Math.pow(2,i))));
            }
        }else {
            CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY, new ItemStack(b, 1, 0), new FluidStack(f, 288));
            CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY, new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), new FluidStack(f, 144));
        }
    }

    private void integrateCompactor(BaseOre b){
        //compactor storage recipes for variant ingots
        BaseIngot ingot = (BaseIngot)ModItems.INGOTS.get(b.name + "Ingot");
        for(int i = 1; i < 5; i ++){
            CompactorManager.addRecipe((int)(CompactorManager.DEFAULT_ENERGY / Math.pow(2, i)), new ItemStack(ingot, 2, i),new ItemStack(ingot, 1, i-1 ), CompactorManager.Mode.ALL );
        }
    }
}
