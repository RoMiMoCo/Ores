package com.craftorio.ores.Items;

import com.craftorio.ores.Ores;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;

import java.util.HashMap;

public class ModItems {
    public static HashMap<String, BaseIngot> INGOTS = new HashMap<>();
    public static HashMap<String, BaseGem> GEMS = new HashMap<>();
    public static HashMap<String, BaseDust> DUSTS = new HashMap<>();
    public static HashMap<String, Item> ARMORS = new HashMap<>();
    public static HashMap<String, Item> TOOLS = new HashMap<>();
    public static HashMap<String, Item> MISC = new HashMap<>();

    public static void init() {

        //for each of the ores we generated in ModBlocks:
        for (BaseOre ore : ModBlocks.ORES) {
            //ingot
            if (ore.genIngots) {
                if (ore.genVariants) {
                    INGOTS.put(ore.name + "Ingot", new BaseIngotWithVariants(ore));
                } else {
                    INGOTS.put(ore.name + "Ingot", new BaseIngot(ore));
                }
            }
            //dusts
            if (ore.genDusts) {
                if (ore.genVariants) {
                    DUSTS.put(ore.name + "Dust", new BaseDustWithVariants(ore));
                } else {
                    DUSTS.put(ore.name + "Dust", new BaseDust(ore));
                }
            }
            //tools
            if (ore.genTools) {
                genTools(ore);
            }
            //armors
            if (ore.genArmor) {
                genArmor(ore);
            }
            //buckets
//            if(OreConfig.genBuckets || ore.genBuckets) {
//               MISC.put(ore.name + "Bucket", new BaseBucket(ore));
//            }

            if (ore.genShields) {
                MISC.put(ore.name + "Shield", new BaseShield(ore));
            }
        }

        //GEMS
        for (BaseOre gem : ModBlocks.GEMS.values()) {

            if (gem.genTools) {
                genTools(gem);
            }

            if (gem.genArmor) {
                genArmor(gem);
            }
        }
    }


    private static void genTools(BaseOre ore) {
        ItemTool.ToolMaterial t = ore.toolMaterial;
        TOOLS.put(ore.name + "Pickaxe", new BasePickaxe(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Pickaxe"));
        TOOLS.put(ore.name + "Axe", new BaseAxe(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Axe"));
        TOOLS.put(ore.name + "Shovel", new BaseShovel(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Shovel"));
        TOOLS.put(ore.name + "Hoe", new BaseHoe(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Hoe"));
        TOOLS.put(ore.name + "Sword", new BaseSword(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Sword"));
        TOOLS.put(ore.name + "Shears", new BaseShears(t, ore.getColor()).setRegistryName(Ores.MODID, ore.name + "Shears"));
    }

    private static void genArmor(BaseOre ore) {
        ItemArmor.ArmorMaterial a = ore.armorMaterial;
        ARMORS.put(ore.name + "Helmet", new BaseArmor(a, 1, EntityEquipmentSlot.HEAD, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Helmet"));
        ARMORS.put(ore.name + "Chestplate", new BaseArmor(a, 1, EntityEquipmentSlot.CHEST, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Chestplate"));
        ARMORS.put(ore.name + "Leggings", new BaseArmor(a, 1, EntityEquipmentSlot.LEGS, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Leggings"));
        ARMORS.put(ore.name + "Boots", new BaseArmor(a, 1, EntityEquipmentSlot.FEET, ore.getColor()).setRegistryName(Ores.MODID, a.name() + "Boots"));
    }
}
