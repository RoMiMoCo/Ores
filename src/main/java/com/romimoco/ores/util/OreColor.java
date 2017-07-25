package com.romimoco.ores.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class OreColor implements IBlockColor, IItemColor {
    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        if(state.getBlock() instanceof IColoredItem)
        {
            return ((IColoredItem)state.getBlock()).getColor();
        }
        return 0;
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if(stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() instanceof IColoredItem){
            return ((IColoredItem)((ItemBlock)stack.getItem()).getBlock()).getColor();
        }
        else if(stack.getItem() instanceof IColoredItem){
            return(((IColoredItem) stack.getItem()).getColor());
        }
        return 0;
    }
}
