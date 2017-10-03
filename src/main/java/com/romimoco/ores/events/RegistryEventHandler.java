package com.romimoco.ores.events;

import com.romimoco.ores.Items.BaseArmor;
import com.romimoco.ores.Items.BaseDust;
import com.romimoco.ores.Items.BaseIngot;
import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.BaseBlock;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.blocks.itemBlocks.ItemBlockBaseOre;
import com.romimoco.ores.crafting.RecipeManager;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreConfig;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryEventHandler
{
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){

        OreLogger.info("Registering Blocks");
        IForgeRegistry<Block> r = event.getRegistry();

        for(Block b : ModBlocks.ORES){
            r.register(b);
        }
        for(Block b : ModBlocks.GEMS){
            r.register(b);
        }

        for(Block b : ModBlocks.BLOCKS.values()){
            r.register(b);
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){

        IForgeRegistry<Item> r = event.getRegistry();
        //Register ItemBlocks for blocks
        for(Block b : ModBlocks.ORES){
            ItemBlock i = new ItemBlockBaseOre(b);

            r.register(i.setRegistryName(b.getRegistryName()));
            String name = ((BaseOre)b).name;
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            OreLogger.debug(name);

            if(OreConfig.genVariants)
            {
                OreDictionary.registerOre("ore"+ name + "poor", new ItemStack(i, 1, 4));
                OreDictionary.registerOre("ore"+ name + "low", new ItemStack(i, 1, 3));
                OreDictionary.registerOre("ore"+ name + "moderate", new ItemStack(i, 1, 2));
                OreDictionary.registerOre("ore"+ name + "high", new ItemStack(i, 1, 1));
                OreDictionary.registerOre("ore"+ name, new ItemStack(i, 1, 0));
            }else {
                OreDictionary.registerOre("ore" + name, i);
            }
        }
        for(Block b : ModBlocks.GEMS){
            ItemBlock i = new ItemBlockBaseOre(b);

            r.register(i.setRegistryName(b.getRegistryName()));
            String name = ((BaseOre)b).name;
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            OreLogger.debug(name);

            if(OreConfig.genVariants)
            {
                OreDictionary.registerOre("ore"+ name + "poor", new ItemStack(i, 1, 4));
                OreDictionary.registerOre("ore"+ name + "low", new ItemStack(i, 1, 3));
                OreDictionary.registerOre("ore"+ name + "moderate", new ItemStack(i, 1, 2));
                OreDictionary.registerOre("ore"+ name + "high", new ItemStack(i, 1, 1));
                OreDictionary.registerOre("ore"+ name, new ItemStack(i, 1, 0));
            }else {
                OreDictionary.registerOre("ore" + name, i);
            }
        }

        for(Block b : ModBlocks.BLOCKS.values()){
            ItemBlock i = new ItemBlock(b);

            r.register(i.setRegistryName(b.getRegistryName()));
            String name = ((BaseBlock)b).name;
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            OreLogger.debug(name);

            OreDictionary.registerOre("block" +name, b);
        }

        //Register the remaining items
        for(Item i: ModItems.INGOTS.values()){
            r.register(i);
            String name = ((BaseIngot)i).name;
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            if(OreConfig.genVariants)
            {
                OreDictionary.registerOre("nugget"+ name, new ItemStack(i, 1, 4));
                OreDictionary.registerOre("shard"+ name, new ItemStack(i, 1, 3));
                OreDictionary.registerOre("chunk"+ name, new ItemStack(i, 1, 2));
                OreDictionary.registerOre("hunk"+ name, new ItemStack(i, 1, 1));
                OreDictionary.registerOre("ingot"+ name, new ItemStack(i, 1, 0));

                //TODO: Revisit making this a custom ingredient.  this is hacky as all hell
                OreDictionary.registerOre("mat"+ name, new ItemStack(i, 1, 4));
                OreDictionary.registerOre("mat"+ name, new ItemStack(i, 1, 3));
                OreDictionary.registerOre("mat"+ name, new ItemStack(i, 1, 2));
                OreDictionary.registerOre("mat"+ name, new ItemStack(i, 1, 1));
                OreDictionary.registerOre("mat"+ name, new ItemStack(i, 1, 0));

            }else {
                OreDictionary.registerOre("ingot" + name, i);
            }
        }

        for(Item i: ModItems.DUSTS.values()){
            r.register(i);
            String name = ((BaseDust)i).name;
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            if(OreConfig.genVariants)
            {
                OreDictionary.registerOre("dust"+ name + "tiny", new ItemStack(i, 1, 4));
                OreDictionary.registerOre("dust"+ name + "small", new ItemStack(i, 1, 3));
                OreDictionary.registerOre("dust"+ name + "med", new ItemStack(i, 1, 2));
                OreDictionary.registerOre("dust"+ name + "large", new ItemStack(i, 1, 1));
                OreDictionary.registerOre("dust"+ name, new ItemStack(i, 1, 0));
            }else {
                OreDictionary.registerOre("dust" + name, i);
            }
        }


        for(Item i: ModItems.TOOLS.values()){
            r.register(i);
        }
        for(Item i: ModItems.ARMORS.values()){
            r.register(i);
        }
        for(Item i: ModItems.MISC.values()){
            r.register(i);
        }

    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event){
        for(Block b : ModBlocks.ORES){
            ((IHasCustomModel)b).initModel();
        }
        for(Block b : ModBlocks.GEMS){
            ((IHasCustomModel)b).initModel();
        }
        for(Block b : ModBlocks.BLOCKS.values()){
            ((IHasCustomModel)b).initModel();
        }
        for(Item i : ModItems.INGOTS.values()){
            ((IHasCustomModel)i).initModel();
        }
        for(Item i : ModItems.DUSTS.values()){
            ((IHasCustomModel)i).initModel();
        }
        for(Item i : ModItems.ARMORS.values()){
            ((IHasCustomModel)i).initModel();
        }
        for(Item i : ModItems.TOOLS.values()){
            ((IHasCustomModel)i).initModel();
        }
        for(Item i : ModItems.MISC.values()){
            ((IHasCustomModel)i).initModel();
        }
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event){
        for(Block b: ModBlocks.ORES){
            RecipeManager.registerSmeltingRecipes((BaseOre)b);

            if(OreConfig.genArmor){
                RecipeManager.registerArmorRecipes((BaseOre)b);
            }

            if(OreConfig.genTools){
                RecipeManager.registerToolRecipes((BaseOre)b);
            }

            if(OreConfig.genFullBlocks){
                RecipeManager.registerMetalBlockRecipes((BaseOre)b);
            }

            if(OreConfig.genVariants && OreConfig.recipes.variantCombinationRecipes){
                RecipeManager.registerVariantCombinationRecipes((BaseOre)b);
            }

            RecipeManager.registerMiscRecipes((BaseOre)b);
        }

    }
}
