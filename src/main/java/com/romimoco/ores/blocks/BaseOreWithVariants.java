package com.romimoco.ores.blocks;

import com.google.gson.JsonObject;
import com.romimoco.ores.Ores;
import com.romimoco.ores.enums.EnumOreValue;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation;


public class BaseOreWithVariants extends BaseOre {


    public static final PropertyEnum PROPERTYOREVALUE = PropertyEnum.create("orevalue", EnumOreValue.class);

    public BaseOreWithVariants(JsonObject oreDefinition){
        super(oreDefinition);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PROPERTYOREVALUE, EnumOreValue.MODERATE));
    }


    @SideOnly(Side.CLIENT)
    public void initModel(){

        for(int i = 0; i < EnumOreValue.values().length; i++)
        {
            setCustomModelResourceLocation(ItemBlock.getItemFromBlock(this), i, new ModelResourceLocation(Ores.NAME+":baseorewithvariants", "orevalue=" + EnumOreValue.byMetadata(i)  ));
        }

        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                EnumOreValue tmp = (EnumOreValue)state.getValue(PROPERTYOREVALUE);
                return new ModelResourceLocation(Ores.NAME+":baseorewithvariants", "orevalue=" + tmp.toString());
            }
        });
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> list){
        for(EnumOreValue value: EnumOreValue.values()){
            list.add(new ItemStack(this, 1, value.getMetadata()));
        }

    }

    @Override
    public int damageDropped(IBlockState state){
        return ((EnumOreValue)state.getValue(PROPERTYOREVALUE)).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(PROPERTYOREVALUE, EnumOreValue.byMetadata(meta));
    }


    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumOreValue)state.getValue(PROPERTYOREVALUE)).getMetadata();
    }



    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[]{PROPERTYOREVALUE});
    }
}
