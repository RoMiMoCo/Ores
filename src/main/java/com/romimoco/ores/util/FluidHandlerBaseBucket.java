package com.romimoco.ores.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

public class FluidHandlerBaseBucket extends FluidHandlerItemStackSimple implements ICapabilityProvider {
    public FluidHandlerBaseBucket(ItemStack stack, int capacity) {
        super(stack, capacity);
    }


    public boolean canFillFluidType(FluidStack fluid){
        return fluid.getFluid() == FluidRegistry.WATER ||
                fluid.getFluid() == FluidRegistry.LAVA ||
                fluid.getFluid().getName().equals("milk") ||
                FluidRegistry.getBucketFluids().contains(fluid.getFluid());
    }
}
