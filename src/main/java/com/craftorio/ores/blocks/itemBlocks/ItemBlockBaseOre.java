package com.craftorio.ores.blocks.itemBlocks;


import com.craftorio.ores.Ores;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.util.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockBaseOre extends ItemBlock {

    public ItemBlockBaseOre(Block blockin) {

        super(blockin);
        //this.setMaxDamage(0);
        if (blockin instanceof BaseOre && ((BaseOre)blockin).genVariants) {
            this.setHasSubtypes(true);
        }
    }


    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (OreConfig.requireResourcePack) {
            return super.getItemStackDisplayName(stack);
        }
        return Ores.proxy.langs.translate(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

}
