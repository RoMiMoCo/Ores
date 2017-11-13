package com.romimoco.ores.integrations;

import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class TiConIntegration implements IOreIntegration {



    public void preInit(FMLPreInitializationEvent event){
        //Set up IMC Stuff here
    }

    public void Init(FMLInitializationEvent event){


        for(Block b : ModBlocks.ORES){
            addMelting((BaseOre)b);
            addCasting((BaseOre)b);
        }
    }


    public void postInit(FMLPostInitializationEvent event){

    }

    private void addMelting(BaseOre b){
        Fluid moltenOre = new ticonFluid(((BaseOre)b));

        if(!FluidRegistry.registerFluid(moltenOre)){ //if the fluid already exists, just use it
            moltenOre = FluidRegistry.getFluid(moltenOre.getName());
        }


        if(b.shouldRegister){
            if(OreConfig.genVariants){
                for(int i = 0; i < 5; i ++){
                    int amt = (int)Math.pow(2, 4-i) * 18;
                    TinkerRegistry.registerMelting(new ItemStack(b, 1, i), moltenOre , amt);
                    TinkerRegistry.registerMelting(new ItemStack(ModItems.INGOTS.get(b.name+"Ingot"), 1, i), moltenOre, amt / 2 );
                    if(OreConfig.genDusts){
                        TinkerRegistry.registerMelting(new ItemStack(ModItems.DUSTS.get(b.name+"Dust"), 1, i), moltenOre, amt / 2 );
                    }
                }
            }else{ //just register the melting for the meta 0's
                TinkerRegistry.registerMelting(b, moltenOre, 288);
                TinkerRegistry.registerMelting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), moltenOre, 144 );
                if(OreConfig.genDusts){
                    TinkerRegistry.registerMelting(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), moltenOre, 144 );
                }
            }
        }else{ //Even if we don't register the ore, we still need to add melting for the ingots / dusts
            if(OreConfig.genVariants){
                for(int i = 0; i < 5; i ++){
                    int amt = (int)Math.pow(2, 4-i) * 18;
                    TinkerRegistry.registerMelting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i), moltenOre, amt / 2 );
                    if(OreConfig.genDusts){
                        TinkerRegistry.registerMelting(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i), moltenOre, amt / 2 );
                    }
                }
            }else{
                TinkerRegistry.registerMelting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), moltenOre, 144 );
                if(OreConfig.genDusts) {
                    TinkerRegistry.registerMelting(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), moltenOre, 144);
                }
            }
        }
    }


    private void addCasting(BaseOre b) {
       if(OreConfig.genVariants) {
           TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 4), TinkerSmeltery.castNugget, FluidRegistry.getFluid(b.name), 9);
           TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), TinkerSmeltery.castIngot, FluidRegistry.getFluid(b.name), 144);
       }else{
           TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 4), TinkerSmeltery.castNugget, FluidRegistry.getFluid(b.name), 16);
           TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), TinkerSmeltery.castIngot, FluidRegistry.getFluid(b.name), 144);
       }
    }

    private class ticonFluid extends FluidMolten{

        public ticonFluid(BaseOre b){
            super(b.name, b.getColor());
            this.setTemperature(100);
        }
    }

}
