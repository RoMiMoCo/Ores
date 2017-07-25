package com.romimoco.ores.crafting;

import com.romimoco.ores.Items.*;
import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseBlock;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.enums.EnumOreValue;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import scala.xml.dtd.impl.Base;

import java.util.Arrays;

/*
 * NOTE: I'm still using the old style recipes here, due to the fact that as far as I can tell, forge 1.12 provides no way of
 * creating JSON recipes at runtime.  This means that this mod may not live longer than 1.12, if JSON recipes are required
 * in 1.13+
 */
public class RecipeManager {

    public static void registerSmeltingRecipes(BaseOre b){
        if(OreConfig.genVariants){
            for(EnumOreValue v: EnumOreValue.values()){
                GameRegistry.addSmelting(new ItemStack(b, 1, v.getMetadata()), new ItemStack(ModItems.INGOTS.get(b.name + "Ingot"), 1, v.getMetadata()), 0.0f);
            }
        }else{
            GameRegistry.addSmelting(b, new ItemStack(ModItems.INGOTS.get(b.name + "Ingot")), 0.0f);
        }
    }

    public static void registerMetalBlockRecipes(BaseOre b){
        String resourcePathBase = "recipe" + b.name;
        String oreDictName = "ingot" + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);

        BaseBlock block = (BaseBlock) ModBlocks.BLOCKS.get(b.name + "Block");
        BaseIngot ingot = (BaseIngot) ModItems.INGOTS.get(b.name + "Ingot");

        registerShapedOreRecipe(resourcePathBase + "Block", new ItemStack(block, 1, 0), "xxx", "xxx", "xxx", 'x', oreDictName);
        if(OreConfig.genVariants) {
            registerShapelessRecipe(resourcePathBase + "BlockToIngots", new ItemStack(ingot, 9, 4), Ingredient.fromStacks(new ItemStack(block)));
        }else{
            registerShapelessRecipe(resourcePathBase + "BlockToIngots", new ItemStack(ingot, 9, 0), Ingredient.fromStacks(new ItemStack(block)));
        }
    }

    public static void registerArmorRecipes(BaseOre b) {
        String resourcePathBase = "recipe" + b.name;
        String oreDictName = "ingot" + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);

        BaseArmor helmet = (BaseArmor)ModItems.ARMORS.get(b.name + "Helmet");
        BaseArmor chestplate = (BaseArmor)ModItems.ARMORS.get(b.name + "Chestplate");
        BaseArmor leggings = (BaseArmor)ModItems.ARMORS.get(b.name + "Leggings");
        BaseArmor boots = (BaseArmor)ModItems.ARMORS.get(b.name + "Boots");

        registerShapedOreRecipe(resourcePathBase + "Helmet", new ItemStack(helmet, 1, 0), "xxx", "x x", 'x', oreDictName);
        registerShapedOreRecipe(resourcePathBase + "Chestplate", new ItemStack(chestplate, 1, 0), "x x", "xxx", "xxx", 'x', oreDictName);
        registerShapedOreRecipe(resourcePathBase + "Leggings", new ItemStack(leggings, 1, 0), "xxx", "x x","x x", 'x', oreDictName);
        registerShapedOreRecipe(resourcePathBase + "Boots", new ItemStack(boots, 1, 0), "x x", "x x", 'x', oreDictName);

    }

    public static void registerToolRecipes(BaseOre b){
        String resourcePathBase = "recipe" + b.name;
        String oreDictName = "ingot" + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);

        BaseAxe axe = (BaseAxe)ModItems.TOOLS.get(b.name + "Axe");
        BaseHoe hoe = (BaseHoe)ModItems.TOOLS.get(b.name + "Hoe");
        BasePickaxe pickaxe = (BasePickaxe)ModItems.TOOLS.get(b.name + "Pickaxe");
        BaseShovel shovel = (BaseShovel) ModItems.TOOLS.get(b.name + "Shovel");
        BaseSword sword = (BaseSword) ModItems.TOOLS.get(b.name + "Sword");

        registerShapedOreRecipe(resourcePathBase + "Axe", new ItemStack(axe, 1, 0), "xx", "xy"," y", 'x', oreDictName, 'y', "stickWood");
        registerShapedOreRecipe(resourcePathBase + "Hoe", new ItemStack(hoe, 1, 0), "xx", " y", " y", 'x', oreDictName, 'y', "stickWood");
        registerShapedOreRecipe(resourcePathBase + "Pickaxe", new ItemStack(pickaxe, 1, 0), "xxx", " y "," y ", 'x', oreDictName, 'y', "stickWood");
        registerShapedOreRecipe(resourcePathBase + "Shovel", new ItemStack(shovel, 1, 0), "x", "y","y", 'x', oreDictName, 'y', "stickWood");
        registerShapedOreRecipe(resourcePathBase + "Sword", new ItemStack(sword, 1, 0), "x", "x", "y", 'x', oreDictName, 'y', "stickWood");
    }


    public static void registerVariantCombinationRecipes(BaseOre b) {
        String resourcePathBase = "recipe" + b.name;
        String oreDictName = "ingot" + b.name.substring(0, 1).toUpperCase() + b.name.substring(1);

        BaseIngotWithVariants baseIngot= (BaseIngotWithVariants) ModItems.INGOTS.get(b.name + "Ingot");

        ItemStack nugget = new ItemStack(baseIngot, 1, 0);
        ItemStack shard = new ItemStack(baseIngot, 1, 1);
        ItemStack chunk = new ItemStack(baseIngot, 1, 2);
        ItemStack hunk = new ItemStack(baseIngot, 1, 3);
        ItemStack ingot = new ItemStack(baseIngot, 1, 4);

        Object[] size2 = new Object[2];
        Object[] size4 = new Object[4];
        Object[] size8 = new Object[8];

        Arrays.fill(size2, Ingredient.fromStacks(nugget));
        Arrays.fill(size4, Ingredient.fromStacks(nugget));
        Arrays.fill(size8, Ingredient.fromStacks(nugget));
        registerShapelessRecipe(resourcePathBase + "nuggetToShard", shard, size2);
        registerShapelessRecipe(resourcePathBase + "nuggetToChunk", chunk, size4 );
        registerShapelessRecipe(resourcePathBase + "nuggetToHunk", hunk, size8 );


        Arrays.fill(size2, Ingredient.fromStacks(shard));
        Arrays.fill(size4, Ingredient.fromStacks(shard));
        Arrays.fill(size8, Ingredient.fromStacks(shard));
        registerShapelessRecipe(resourcePathBase + "shardToChunk", chunk, size2 );
        registerShapelessRecipe(resourcePathBase + "shardToHunk", hunk, size4 );
        registerShapelessRecipe(resourcePathBase + "shardToIngot", ingot, size8 );

        Arrays.fill(size2, Ingredient.fromStacks(chunk));
        Arrays.fill(size4, Ingredient.fromStacks(chunk));
        registerShapelessRecipe(resourcePathBase + "chunkToHunk", hunk, size2 );
        registerShapelessRecipe(resourcePathBase + "chunkToIngot", ingot,size4 );


        Arrays.fill(size2, Ingredient.fromStacks(hunk));
        registerShapelessRecipe(resourcePathBase + "hunkToIngot", ingot, size2);



        registerShapelessRecipe(resourcePathBase + "ingotToHunk", new ItemStack(baseIngot, 2, 3), Ingredient.fromStacks(ingot) );
        registerShapelessRecipe(resourcePathBase + "hunkToChunk", new ItemStack(baseIngot, 2, 2), Ingredient.fromStacks(hunk) );
        registerShapelessRecipe(resourcePathBase + "chunkToShard", new ItemStack(baseIngot, 2, 1), Ingredient.fromStacks(chunk) );
        registerShapelessRecipe(resourcePathBase + "shardToNugget", new ItemStack(baseIngot, 2, 0), Ingredient.fromStacks(shard) );
    }


    /*
     * The Following methods are used in accordance with the CoFH "Don't Be a Jerk" License
     * All credit goes to the CoFH team, I am in no way claiming any ownership, partial or ortherwise
     * of the following code
     * (I'm not a jerk)
     */
    private static void registerShapedOreRecipe(String id, ItemStack output, Object... recipe ){

        ResourceLocation location = new ResourceLocation(Ores.MODID, id);
        ShapedOreRecipe or = new ShapedOreRecipe(location, output, recipe);
        or.setRegistryName(location);
        GameData.register_impl(or);
    }

    private static void registerShapelessOreRecipe(String id, ItemStack output, Object... recipe ){

        ResourceLocation location = new ResourceLocation(Ores.MODID, id);
        ShapelessOreRecipe or = new ShapelessOreRecipe(location, output, recipe);
        or.setRegistryName(location);
        GameData.register_impl(or);
    }


    private static void registerShapelessRecipe(String id, ItemStack output, Object... recipe ){

        ResourceLocation location = new ResourceLocation(Ores.MODID, id);
        ShapelessRecipes or = new ShapelessRecipes(location.getResourceDomain(), output, buildInput(recipe));
        or.setRegistryName(location);
        GameData.register_impl(or);
    }



    public static NonNullList<Ingredient> buildInput(Object[] input){
        NonNullList<Ingredient> list = NonNullList.create();

        for (Object obj : input) {
            if (obj instanceof Ingredient) {
                list.add((Ingredient) obj);
            } else {
                Ingredient ingredient = CraftingHelper.getIngredient(obj);

                if (ingredient == null) {
                    ingredient = Ingredient.EMPTY;
                }
                list.add(ingredient);
            }
        }
        return list;
    }
}
