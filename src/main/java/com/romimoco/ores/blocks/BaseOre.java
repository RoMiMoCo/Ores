package com.romimoco.ores.blocks;

import com.google.gson.JsonObject;
import com.romimoco.ores.Ores;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation;


public class BaseOre extends BlockOre implements IColoredItem, IHasCustomModel{

    private int color;
    public String name;
    private int customHarvestLevel;

    public BaseOre(JsonObject oreDefinition){
        super();

        //default values in case of missing json elements
        int color = 0x000000;
        float hardness = 3.0f;
        int harvestLevel = 1;


        this.name = oreDefinition.get("Name").getAsString();

        try {
            color = Integer.parseInt(oreDefinition.get("Color").getAsString().substring(2), 16);
        }catch (Exception e){
        }

        try {
            hardness = oreDefinition.get("Hardness").getAsFloat();
            //Clamp the hardness values to keep tools and armor inline with vanilla.  May be a bad idea in the long run
            if(hardness < 2.0f){
                hardness = 2.0f;
            }else if (hardness > 4.5f){
                hardness = 4.5f;
            }
        }catch (Exception e){
        }

        try {
            harvestLevel = oreDefinition.get("Level").getAsInt();
        }catch (Exception e){
        }

        this.color = color;
        this.setHardness(hardness);

        //save the harvest level, so we can use it with our toolMaterial
        this.customHarvestLevel = harvestLevel;
        this.setHarvestLevel("pickaxe", harvestLevel);

        this.setUnlocalizedName(Ores.MODID +".ore" + name);
        this.setRegistryName(Ores.MODID, name);
        this.setCreativeTab(CreativeTabs.MISC);
    }


    @SideOnly(Side.CLIENT)
    public void initModel(){

        setCustomModelResourceLocation(ItemBlock.getItemFromBlock(this), 0, new ModelResourceLocation(Ores.NAME+":baseore"));

        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(Ores.NAME+":baseore");
            }
        });
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

    public int getColor() {
        return this.color;
    }

    public float getHardness(){return this.blockHardness;}

    public int getHarvestLevel(){
        return this.customHarvestLevel;
    }


}
