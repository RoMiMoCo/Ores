package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.UniversalBucket;

import java.util.HashMap;

public class ModItems {
    public static HashMap<String, Item> INGOTS = new HashMap<>();
    public static HashMap<String, Item> GEMS = new HashMap<>();
    public static HashMap<String,Item> DUSTS = new HashMap<>();
    public static HashMap<String,Item> ARMORS = new HashMap<>();
    public static HashMap<String,Item> TOOLS = new HashMap<>();
    public static HashMap<String,Item> MISC = new HashMap<>();

    public static void init(){

        //for each of the ores we generated in ModBlocks:
        for(Block b : ModBlocks.ORES){

            BaseOre ore = (BaseOre)b;
            //ingot
            if(OreConfig.genIngots) {
                if (OreConfig.genVariants) {
                    INGOTS.put(ore.name + "Ingot", new BaseIngotWithVariants(ore));
                } else {
                    INGOTS.put(ore.name + "Ingot", new BaseIngot(ore));
                }
            }
            //dusts
            if(OreConfig.genDusts) {
                if (OreConfig.genVariants) {
                    DUSTS.put(ore.name + "Dust", new BaseDustWithVariants(ore));
                } else {
                    DUSTS.put(ore.name + "Dust", new BaseDust(ore));
                }
            }
            //tools
            if(OreConfig.genTools){
                genTools(ore);
            }
            //armors
            if(OreConfig.genArmor){
                genArmor(ore);
            }
            //buckets
           // if(OreConfig.genBuckets) {
            //    MISC.put(ore.name + "Bucket", new BaseBucket(ore));
           // }
        }

        //GEMS
        for(Item i : ModItems.GEMS.values()){
            BaseOre ore = (BaseOre)ModBlocks.GEMS.get(((BaseGem)i).name);

            if(OreConfig.genTools){
                genTools(ore);
            }

            if(OreConfig.genArmor) {
                genArmor(ore);
            }
        }
    }


    private static void genTools(BaseOre ore){
                ItemTool.ToolMaterial t = EnumHelper.addToolMaterial(ore.name, ore.getHarvestLevel() + 1, (int) (ore.getHardness() * 100), ore.getHardness() * 2, ore.getHardness() * .66f, 9);
                TOOLS.put(ore.name + "Pickaxe", new BasePickaxe(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Pickaxe"));
                TOOLS.put(ore.name + "Axe", new BaseAxe(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Axe"));
                TOOLS.put(ore.name + "Shovel", new BaseShovel(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Shovel"));
                TOOLS.put(ore.name + "Hoe", new BaseHoe(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Hoe"));
                TOOLS.put(ore.name + "Sword", new BaseSword(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Sword"));
                TOOLS.put(ore.name + "Shears", new BaseShears(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Shears"));
    }

    private static void genArmor(BaseOre ore){
            int[] durabilities = new int[]{(int) (ore.getHardness() / 3.0f * 2), (int) (ore.getHardness() / 3.0f * 5), (int) (ore.getHardness() / 3.0f * 6), (int) (ore.getHardness() / 3.0f * 2)};
            ItemArmor.ArmorMaterial a = EnumHelper.addArmorMaterial(ore.name, Ores.NAME + ":baseArmor", (int) (ore.getHardness() * 5), durabilities, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
            ARMORS.put(ore.name + "Helmet", new BaseArmor(a, 1, EntityEquipmentSlot.HEAD, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Helmet"));
            ARMORS.put(ore.name + "Chestplate", new BaseArmor(a, 1, EntityEquipmentSlot.CHEST, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Chestplate"));
            ARMORS.put(ore.name + "Leggings", new BaseArmor(a, 1, EntityEquipmentSlot.LEGS, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Leggings"));
            ARMORS.put(ore.name + "Boots", new BaseArmor(a, 1, EntityEquipmentSlot.FEET, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Boots"));
    }
}
