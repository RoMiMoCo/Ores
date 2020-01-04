package com.craftorio.ores.enums;

import com.craftorio.ores.Ores;
import com.craftorio.ores.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public enum EnumOreValue implements IStringSerializable {
    THE_END_POOR(14, "poor" + DimensionType.THE_END.getSuffix(), DimensionType.THE_END.getId(), 4),
    THE_END_LOW(13, "low" + DimensionType.THE_END.getSuffix(), DimensionType.THE_END.getId(), 3),
    THE_END_MODERATE(12, "moderate" + DimensionType.THE_END.getSuffix(), DimensionType.THE_END.getId(), 2),
    THE_END_HIGH(11, "high" + DimensionType.THE_END.getSuffix(), DimensionType.THE_END.getId(), 1),
    THE_END_RICH(10, "rich" + DimensionType.THE_END.getSuffix(), DimensionType.THE_END.getId(), 0),

    NETHER_POOR(9, "poor" + DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), 4),
    NETHER_LOW(8, "low" + DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), 3),
    NETHER_MODERATE(7, "moderate" + DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), 2),
    NETHER_HIGH(6, "high" + DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), 1),
    NETHER_RICH(5, "rich" + DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), 0),

    OVERWORLD_POOR(4, "poor" + DimensionType.OVERWORLD.getSuffix(), DimensionType.OVERWORLD.getId(), 4),
    OVERWORLD_LOW(3, "low" + DimensionType.OVERWORLD.getSuffix(), DimensionType.OVERWORLD.getId(), 3),
    OVERWORLD_MODERATE(2, "moderate" + DimensionType.OVERWORLD.getSuffix(), DimensionType.OVERWORLD.getId(), 2),
    OVERWORLD_HIGH(1, "high" + DimensionType.OVERWORLD.getSuffix(), DimensionType.OVERWORLD.getId(), 1),
    OVERWORLD_RICH(0, "rich" + DimensionType.OVERWORLD.getSuffix(), DimensionType.OVERWORLD.getId(), 0);

    public static ArrayList<EnumOreValue> overworldOres = new ArrayList<EnumOreValue>() {{
        add(OVERWORLD_RICH);
        add(OVERWORLD_HIGH);
        add(OVERWORLD_MODERATE);
        add(OVERWORLD_LOW);
        add(OVERWORLD_POOR);
    }};
    public static ArrayList<EnumOreValue> netherOres = new ArrayList<EnumOreValue>() {{
        add(NETHER_RICH);
        add(NETHER_HIGH);
        add(NETHER_MODERATE);
        add(NETHER_LOW);
        add(NETHER_POOR);
    }};
    public static ArrayList<EnumOreValue> theEndOres = new ArrayList<EnumOreValue>() {{
        add(THE_END_RICH);
        add(THE_END_HIGH);
        add(THE_END_MODERATE);
        add(THE_END_LOW);
        add(THE_END_POOR);
    }};

    private final int meta;
    private final String name;
    private final int worldId;
    private final int valueId;
    private static final HashMap<Integer, EnumOreValue> META_LOOKUP = new HashMap<>();

    public int getMetadata() {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }


    public static EnumOreValue byMetadata(int meta) {
        EnumOreValue v = META_LOOKUP.get(meta);

        return null != v ? v : OVERWORLD_RICH;
    }

    public static EnumOreValue byWorld(World world) {
        return byWorld(world.provider.getDimensionType().getId());
    }

    public static EnumOreValue byWorld(int dimId) {
        for (EnumOreValue value : values()) {
            if (dimId == value.worldId && 0 == value.valueId) {
                return value;
            }
        }
        return OVERWORLD_RICH;
    }

    public static EnumOreValue byWorldValue(World world, int valueId) {
        return byWorldValue(world.provider.getDimensionType().getId(), valueId);
    }

    public static EnumOreValue byWorldValue(int dimId, int valueId) {
        for (EnumOreValue value : values()) {
            if (dimId == value.worldId && valueId == value.valueId) {
                return value;
            }
        }
        return OVERWORLD_RICH;
    }

    public static String ingotNameByMetadata(int meta) {
        switch (byMetadata(meta).valueId) {
            case 4:
                return "nugget";
            case 3:
                return "shard";
            case 2:
                return "chunk";
            case 1:
                return "hunk";
            case 0:
            default:
                return "ingot";
        }
    }

    public static String dustNameByMetadata(int meta) {
        switch (byMetadata(meta).valueId) {
            case 4:
                return "tiny";
            case 3:
                return "small";
            case 2:
                return "medium";
            case 1:
                return "large";
            case 0:
            default:
                return "huge";
        }
    }

    public String getName() {
        return this.name;
    }
    public int getValueId() {
        return valueId;
    }
    public int getVariant() {
        return getValueId();
    }

    public int getWorldId() {
        return worldId;
    }

    public int getDimId() {
        return getWorldId();
    }

    public static ArrayList<EnumOreValue> getWorldVariants(int dimId)
    {
        if (DimensionType.NETHER.getId() == dimId) {
            return netherOres;
        } else if (DimensionType.THE_END.getId() == dimId) {
            return theEndOres;
        } else {
            return overworldOres;
        }
    }

    public ModelResourceLocation getModelResourceLocator(String suffix)
    {
        if (netherOres.contains(this)) {
            return new ModelResourceLocation(Ores.NAME + ":netherore" + suffix);
        } else if (theEndOres.contains(this)) {
            return new ModelResourceLocation(Ores.NAME + ":theendore" + suffix);
        } else {
            return new ModelResourceLocation(Ores.NAME + ":baseore" + suffix);
        }
    }

    public ModelResourceLocation getModelResourceLocator(String suffix, String variantIn)
    {
        if (netherOres.contains(this)) {
            return new ModelResourceLocation(Ores.NAME + ":netherore" + suffix, variantIn);
        } else if (theEndOres.contains(this)) {
            return new ModelResourceLocation(Ores.NAME + ":theendore" + suffix, variantIn);
        } else {
            return new ModelResourceLocation(Ores.NAME + ":baseore" + suffix, variantIn);
        }
    }

    EnumOreValue(int meta, String name, int worldId, int valueId) {
        this.meta = meta;
        this.name = name;
        this.worldId = worldId;
        this.valueId = valueId;
    }

    static {
        for (EnumOreValue value : values()) {
            META_LOOKUP.put(value.getMetadata(), value);
        }
    }
}


