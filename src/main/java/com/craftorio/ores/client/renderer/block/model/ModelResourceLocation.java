package com.craftorio.ores.client.renderer.block.model;

import com.craftorio.ores.util.OreLogger;
import net.minecraft.util.ResourceLocation;

public class ModelResourceLocation extends net.minecraft.client.renderer.block.model.ModelResourceLocation
{
    public ModelResourceLocation(String pathIn)
    {
        super(pathIn);
        //OreLogger.info("Located resource for pathIn:" + pathIn);
    }

    public ModelResourceLocation(ResourceLocation location, String variantIn)
    {
        super(location, variantIn);
        //OreLogger.info("Located resource for path: " + location.getPath() + " and variantIn:" + variantIn);
    }

    public ModelResourceLocation(String location, String variantIn)
    {
        super(location, variantIn);
        //OreLogger.info("Located resource for location: " + location + " and variantIn:" + variantIn);
    }
}
