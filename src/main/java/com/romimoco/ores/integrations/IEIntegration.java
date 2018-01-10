package com.romimoco.ores.integrations;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.IERecipes;
import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class IEIntegration implements IOreIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {


    }

    @Override
    public void Init(FMLInitializationEvent event) {
        for(Block b : ModBlocks.ORES){
            if(OreConfig.genDusts) {
                addCrushing((BaseOre) b);
            }
            addArcSmelting((BaseOre)b);
        }
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void addCrushing(BaseOre b) {
        if(b.shouldRegister){
            if(OreConfig.genVariants){
                for(int i = 0; i < 5; i ++){
                    if(i==0){
                        IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 2, i), b, 6000);
                        IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i), ModItems.INGOTS.get(b.name + "Ingot"), 3600);
                    } else {
                        IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i-1), new ItemStack(b, 1, i), 6000);
                        IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i), new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i), (int)Math.pow(2,4-i) * 225);
                    }
                }
            }else{ //just register for the meta 0's
                IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 2, 0), b, 6000);
                IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), ModItems.INGOTS.get(b.name + "Ingot"), 3600);
            }
        }else{ //Even if we don't register the ore, we still need to add melting for the ingots / dusts
            if(OreConfig.genVariants){
                for(int i = 0; i < 5; i ++) {
                    IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i), ModItems.INGOTS.get(b.name + "Ingot"), (int) Math.pow(2, 4 - i) * 225);
                }
            }else{
                    IERecipes.addCrusherRecipe(new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), ModItems.INGOTS.get(b.name + "Ingot"), 3600);
            }
        }

    }

    public void addArcSmelting(BaseOre b){

        if(b.shouldRegister){
            if(OreConfig.genVariants){
                for(int i = 0; i < 5; i ++){
                    if(i==0){
                        IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 2, i), b, 200, 512, new ItemStack(IEContent.itemMaterial,1,7)).setSpecialRecipeType("Ores");
                        if(OreConfig.genDusts){
                            IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i), new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), 200, 512, new ItemStack(IEContent.itemMaterial,1,7)).setSpecialRecipeType("Ores");
                        }
                    } else {
                        IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i-1), new ItemStack(b, 1, i), 200, 512, new ItemStack(IEContent.itemMaterial,1,7)).setSpecialRecipeType("Ores");
                        if(OreConfig.genDusts) {
                            IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i), new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i), (int) Math.pow(2, 4 - i) * 12, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                        }
                    }
                }
            }else{ //just register  for the meta 0's
                IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 2, 0), b, 200, 512, new ItemStack(IEContent.itemMaterial,1,7)).setSpecialRecipeType("Ores");
                if(OreConfig.genDusts) {
                    IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), 200, 512, new ItemStack(IEContent.itemMaterial, 1, 7)).setSpecialRecipeType("Ores");
                }
            }
        }else{ //Even if we don't register the ore, we still need to add smelting for the  dusts
            if(!OreConfig.genDusts){
                return;
            }
            if(OreConfig.genVariants){
                for(int i = 0; i < 5; i ++) {
                    IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, i), new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, i),(int)Math.pow(2,4-i) * 12, 512, new ItemStack(IEContent.itemMaterial,1,7)).setSpecialRecipeType("Ores");
                }
            }else{
                IERecipes.addArcRecipe(new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, 0), new ItemStack(ModItems.DUSTS.get(b.name + "Dust"), 1, 0), 200, 512, new ItemStack(IEContent.itemMaterial,1,7)).setSpecialRecipeType("Ores");
            }
        }
    }
}
