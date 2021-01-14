package com.craftorio.ores.blocks;

import com.craftorio.ores.Items.BaseGem;
import com.craftorio.ores.Ores;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.craftorio.ores.Items.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BaseGemOre extends BaseOre implements IColoredItem, IHasCustomModel {

    private String drop;
    private int dropMeta = 0;

    public BaseGemOre(JsonObject gemDefinition) {
        super(gemDefinition);

        float hardness = 4.5f;
        String drop = "minecraft:coal";


        try {
            hardness = gemDefinition.get("Hardness").getAsFloat();
        } catch (Exception e) {
        }

        try {
            drop = gemDefinition.get("Drops").toString();
        } catch (Exception e) {
        }

        if (drop.charAt(0) != '{') {
            drop = drop.substring(1, drop.length() - 1); //trim the excess quotes off
            this.drop = drop;
            if (drop.contains("/")) {
                this.drop = drop.substring(0, drop.indexOf("/"));
                this.dropMeta = Integer.parseInt(drop.substring(drop.indexOf("/") + 1));
            }
        } else {
            JsonParser parser = new JsonParser();
            JsonObject def = (JsonObject) parser.parse(drop);
            BaseGem gem = new BaseGem(def, this);

            this.drop = gem.getTranslationKey().replaceFirst("item.", "");
            String type = def.get("Type").getAsString();

            if (type == null || !type.equals("gem")) {
                ModItems.MISC.put(gem.type + this.name, gem);
            } else {
                ModItems.GEMS.put(this.name + "Gem", gem);
            }
        }
        this.setHardness(hardness);
    }

    @Override
    protected void initItemStats(JsonElement itemStats) {

        //Tool Stats
        float toolEfficiency = this.getHardness() * 2;
        float toolDamage = this.getHardness() * .66f;

        //Armor Stats
        int armorClassMod = (int) (this.getHardness() / 3.0f);
        float armorToughness = 0.0f;

        //Common Stats
        int enchantability = 9;
        float durabilityMod = this.getHardness() * 5;

        if (itemStats != null) {
            //Read JSON here
            JsonObject stats = itemStats.getAsJsonObject();

            try {
                toolEfficiency = stats.get("Efficiency").getAsFloat();
            } catch (Exception e) {
            }


            try {
                toolDamage = stats.get("Damage").getAsFloat();
            } catch (Exception e) {
            }

            try {
                armorClassMod = stats.get("ArmorClass").getAsInt();
            } catch (Exception e) {
            }

            try {
                armorToughness = stats.get("Toughness").getAsFloat();
            } catch (Exception e) {
            }

            try {
                enchantability = stats.get("Enchantability").getAsInt();
            } catch (Exception e) {
            }

            try {
                durabilityMod = stats.get("DurabilityMod").getAsFloat();
            } catch (Exception e) {
            }
        }

        int[] armorPieceMods = new int[]{armorClassMod * 3, armorClassMod * 6, armorClassMod * 8, armorClassMod * 3};
        //construct materials
        this.toolMaterial = EnumHelper.addToolMaterial(this.name, this.getHarvestLevel() + 1, (int) ((durabilityMod * 48) - 23), toolEfficiency, toolDamage, enchantability);
        this.armorMaterial = EnumHelper.addArmorMaterial(this.name, Ores.NAME + ":baseArmor", (int) durabilityMod, armorPieceMods, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, armorToughness);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return getItemDropped();
    }

    public Item getItemDropped() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(drop));
    }

    public int getItemMetaDropped() {
        return this.dropMeta;
    }



    @Override
    public int damageDropped(IBlockState state) {
        return this.dropMeta;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int count;
        int max = 5 - this.getMetaVariantFromState(state);
        int min = max > 1 ? max-1 : max;

        count = random.ints(min, (max + 1)).findFirst().getAsInt();

        return count + quantityDroppedWithBonus(fortune, random);
    }
}
