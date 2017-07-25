package com.romimoco.ores.blocks.itemBlocks;


import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBaseOre extends ItemBlock {

    public ItemBlockBaseOre(Block blockin){

        super(blockin);
        //this.setMaxDamage(0);
        if(OreConfig.genVariants) {
            this.setHasSubtypes(true);
        }

    }


    @Override
    public int getMetadata(int metadata){
        return metadata;
    }

}
