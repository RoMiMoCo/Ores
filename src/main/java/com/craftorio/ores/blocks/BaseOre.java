package com.craftorio.ores.blocks;

import com.craftorio.ores.Ores;
import com.craftorio.ores.enums.EnumOreValue;
import com.craftorio.ores.util.IColoredItem;
import com.craftorio.ores.util.IHasCustomModel;
import com.craftorio.ores.util.OreLogger;
import com.craftorio.ores.util.StringUtil;
import com.craftorio.ores.world.OreGenDefinition;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockOre;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

import static net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation;


public class BaseOre extends BlockOre implements IColoredItem, IHasCustomModel {

    private int color;
    public String name;
    private int customHarvestLevel;
    public boolean shouldRegister = false;
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
    public OreGenDefinition oreGenDefinition;

    public ArrayList<Integer> dimensions = new ArrayList<>();

    public static final PropertyEnum PROPERTYOREVALUE = PropertyEnum.create("orevalue", EnumOreValue.class);

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

        JsonObject generation = getOreDefinitionAsObject(oreDefinition, "Generation");
        if (null != generation) {
            shouldRegister = true;
            oreGenDefinition = new OreGenDefinition(this, generation);
            JsonArray dimensions = getOreDefinitionAsArray(generation, "Dimensions");
            if (null != dimensions) {
                for (JsonElement d : dimensions) {
                    try {
                        Integer dimId = getJsonInt(d, "ID");
                        if (!this.dimensions.contains(dimId)) {
                            this.dimensions.add(dimId);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        if (0 == this.dimensions.size()) {
            this.dimensions.add(DimensionType.OVERWORLD.getId());
        }

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
        for (int d : dimensions) {
            this.setDefaultState(this.blockState.getBaseState().withProperty(PROPERTYOREVALUE, EnumOreValue.byWorld(d)));
            break;
        }
        this.setTranslationKey(Ores.MODID + ":ore" + name);
        this.setRegistryName(Ores.MODID, name);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        OreLogger.localize(this.getTranslationKey() + ".name=" + StringUtil.toSentenceCase(this.name) + " Ore");
    }

    protected boolean getOreDefinitionAsBoolean(JsonObject oreDefinition, String memberName) {
        return oreDefinition.has(memberName) && oreDefinition.get(memberName).getAsBoolean();
    }

    protected JsonArray getOreDefinitionAsArray(JsonObject oreDefinition, String memberName) {
        return oreDefinition.has(memberName) ? oreDefinition.get(memberName).getAsJsonArray() : null;
    }

    protected JsonObject getOreDefinitionAsObject(JsonObject oreDefinition, String memberName) {
        return oreDefinition.has(memberName) ? oreDefinition.get(memberName).getAsJsonObject() : null;
    }

    private int getJsonInt(JsonElement el, String k)
    {
        return el.getAsJsonObject().get(k).getAsInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> list) {
        for (int d : dimensions) {
            for (EnumOreValue value : EnumOreValue.values()) {
                if (value.getVariant() > 0 && !this.genVariants) {
                    continue;
                }
                if (value.getDimId() == d) {
                    list.add(new ItemStack(this, 1, value.getMetadata()));
                }
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumOreValue) state.getValue(PROPERTYOREVALUE)).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(PROPERTYOREVALUE, EnumOreValue.byMetadata(meta));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        if (this.genVariants) {
            initModelWithVariants("withvariants");
        } else {
            initModel("withvariants");
        }
    }

    protected void initModel(String suffix) {

        for (int d : dimensions) {
            EnumOreValue oreValue = EnumOreValue.byWorld(d);
            setCustomModelResourceLocation(
                    ItemBlock.getItemFromBlock(this),
                    oreValue.getMetadata(),
                    oreValue.getModelResourceLocator(suffix)
            );
        }

        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                EnumOreValue oreValue = (EnumOreValue) state.getValue(PROPERTYOREVALUE);
                return oreValue.getModelResourceLocator(suffix, "orevalue=" + oreValue.toString());
            }
        });
    }

    protected void initModelWithVariants(String suffix) {

        for (int d : dimensions) {
            for ( EnumOreValue oreValue : EnumOreValue.getWorldVariants(d)) {
                setCustomModelResourceLocation(
                        ItemBlock.getItemFromBlock(this),
                        oreValue.getMetadata(),
                        oreValue.getModelResourceLocator(suffix, "orevalue=" + oreValue.toString())
                );
            }
        }

        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                EnumOreValue oreValue = (EnumOreValue) state.getValue(PROPERTYOREVALUE);
                return oreValue.getModelResourceLocator(suffix, "orevalue=" + oreValue.toString());
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
    public int getMetaFromState(IBlockState state) {
        return ((EnumOreValue) state.getValue(PROPERTYOREVALUE)).getMetadata();
    }

    public int getMetaVariantFromState(IBlockState state) {
        return ((EnumOreValue) state.getValue(PROPERTYOREVALUE)).getValueId();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{PROPERTYOREVALUE});
    }


    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
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
