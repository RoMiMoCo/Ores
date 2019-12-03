package com.romimoco.ores.blocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreLogger;
import com.romimoco.ores.util.StringUtil;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation;


public class BaseOre extends BlockOre implements IColoredItem, IHasCustomModel {

    private int color;
    public String name;
    private int customHarvestLevel;
    public boolean shouldRegister = true;
    public ItemTool.ToolMaterial toolMaterial;
    public ItemArmor.ArmorMaterial armorMaterial;
    public boolean genVariants = false;
    public boolean genDusts = false;
    public boolean genIngots = false;
    public boolean genFullBlocks = false;
    public boolean genTools = false;
    public boolean genArmor = false;
    public boolean genBuckets = false;
    public boolean genFluids = false;
    public boolean genShields = false;

    public BaseOre(JsonObject oreDefinition) {
        super();

        //default values in case of missing json elements
        int color = 0x000000;
        float hardness = 3.0f;
        int harvestLevel = 1;


        this.name = oreDefinition.get("Name").getAsString();
        this.genVariants = getOreDefinitionAsBoolean(oreDefinition, "genVariants");
        this.genDusts = getOreDefinitionAsBoolean(oreDefinition, "genDusts");
        this.genIngots = getOreDefinitionAsBoolean(oreDefinition, "genIngots");
        this.genFullBlocks = getOreDefinitionAsBoolean(oreDefinition, "genFullBlocks");
        this.genTools = getOreDefinitionAsBoolean(oreDefinition, "genTools");
        this.genArmor = getOreDefinitionAsBoolean(oreDefinition, "genArmor");
        this.genBuckets = getOreDefinitionAsBoolean(oreDefinition, "genBuckets");
        this.genFluids = getOreDefinitionAsBoolean(oreDefinition, "genFluids");
        this.genShields = getOreDefinitionAsBoolean(oreDefinition, "genShields");

        try {
            color = Integer.parseInt(oreDefinition.get("Color").getAsString().substring(2), 16);
        } catch (Exception e) {
        }

        try {
            hardness = oreDefinition.get("Hardness").getAsFloat();
        } catch (Exception e) {
        }

        try {
            harvestLevel = oreDefinition.get("Harvestlevel").getAsInt();
        } catch (Exception e) {
        }

        this.color = color;
        this.setHardness(hardness);

        //save the harvest level, so we can use it with our toolMaterial
        this.customHarvestLevel = harvestLevel;
        this.setHarvestLevel("pickaxe", harvestLevel);

        initItemStats(oreDefinition.get("ItemStats"));

        this.setUnlocalizedName(Ores.MODID + ":ore" + name);
        this.setRegistryName(Ores.MODID, name);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        OreLogger.localize(this.getUnlocalizedName() + ".name=" + StringUtil.toSentenceCase(this.name) + " Ore");
    }

    protected boolean getOreDefinitionAsBoolean(JsonObject oreDefinition, String memberName) {
        return oreDefinition.has(memberName) && oreDefinition.get(memberName).getAsBoolean();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

        setCustomModelResourceLocation(ItemBlock.getItemFromBlock(this), 0, new ModelResourceLocation(Ores.NAME + ":baseore"));

        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(Ores.NAME + ":baseore");
            }
        });
    }

    protected void initItemStats(JsonElement itemStats) {

        //Tool Stats
        float toolEfficiency = this.getHardness() * 2;
        float toolDamage = this.getHardness() * .66f;

        //Armor Stats
        int armorClassMod = (int) (this.getHardness() / 3.0f);
        //(int) (this.getHardness() / 3.0f * 2),
        //(int) (this.getHardness() / 3.0f * 5),
        //(int) (this.getHardness() / 3.0f * 6),
        //(int) (this.getHardness() / 3.0f * 2);
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

        int[] armorPieceMods = new int[]{armorClassMod * 2, armorClassMod * 5, armorClassMod * 6, armorClassMod * 2};
        //construct materials
        this.toolMaterial = EnumHelper.addToolMaterial(this.name, this.getHarvestLevel() + 1, (int) ((durabilityMod * 27.25) - 158.75), toolEfficiency, toolDamage, enchantability);
        this.armorMaterial = EnumHelper.addArmorMaterial(this.name, Ores.NAME + ":baseArmor", (int) durabilityMod, armorPieceMods, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, armorToughness);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public int getColor() {
        return this.color;
    }

    public float getHardness() {
        return this.blockHardness;
    }

    public int getHarvestLevel() {
        return this.customHarvestLevel;
    }


}
