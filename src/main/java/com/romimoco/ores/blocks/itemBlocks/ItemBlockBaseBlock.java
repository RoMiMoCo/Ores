package com.romimoco.ores.blocks.itemBlocks;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBaseBlock extends ItemBlock {
    public ItemBlockBaseBlock(Block b) {
        super(b);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(OreConfig.requireResourcePack) {
            return super.getItemStackDisplayName(stack);
        }
        return Ores.proxy.langs.translate(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
}
