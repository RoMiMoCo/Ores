package com.romimoco.ores.blocks;

import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlock extends Block implements IColoredItem, IHasCustomModel{

    private int color = 0x000000;
    public String name = "";
    private String type = "";

    public BaseBlock(BaseOre ore) {
        super(Material.IRON);
        this.setSoundType(SoundType.METAL);
        this.color = ore.getColor();
        this.name = "block" + ore.name;

        if(ore instanceof BaseGemOre){
            this.type="Gem";
            this.setSoundType(SoundType.STONE);
        }else{
            this.type="Metal";
        }

        this.setUnlocalizedName(Ores.MODID +":" + name);
        this.setRegistryName(Ores.MODID, name);
        this.setCreativeTab(CreativeTabs.MISC);
        OreLogger.localize(this.getUnlocalizedName() + ".name=" + ore.name.substring(0,1).toUpperCase() + ore.name.substring(1) + " Block");

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {


        if(this.type.equals("Gem")){

            ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(this), 0, new ModelResourceLocation( Ores.NAME + ":basegemblock"));

            ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    return new ModelResourceLocation(Ores.NAME+":basegemblock");
                }
            });
        }else{
            ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(this), 0, new ModelResourceLocation( Ores.NAME + ":baseblock"));

            ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    return new ModelResourceLocation(Ores.NAME+":baseblock");
                }
            });
        }
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getColor() {
        return this.color;
    }

}
