package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.enums.EnumOreValue;
import com.romimoco.ores.util.IColoredItem;
import com.romimoco.ores.util.IHasCustomModel;
import com.romimoco.ores.util.OreLogger;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseDustWithVariants extends BaseDust implements IHasCustomModel, IColoredItem{


    public static final PropertyEnum PROPERTYOREVALUE = PropertyEnum.create("orevalue", EnumOreValue.class);

    public BaseDustWithVariants(BaseOre b){
       super(b);
       this.setHasSubtypes(true);
       this.setCreativeTab(CreativeTabs.MISC);

       for(EnumOreValue v :EnumOreValue.values()) {
           OreLogger.localize(this.getUnlocalizedName(new ItemStack(this, 1, v.getMetadata())) + ".name="+ EnumOreValue.dustNameByMetadata(v.getMetadata()).substring(0,1).toUpperCase() +
                                                                    EnumOreValue.dustNameByMetadata(v.getMetadata()).substring(1) + " pile of " + b.name.substring(0,1).toUpperCase() + b.name.substring(1) + " Dust" );
       }
    }


    @SideOnly(Side.CLIENT)
    public void initModel(){

        for(int i = 0; i < EnumOreValue.values().length; i++)
        {
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(Ores.NAME+":basedust_"+ EnumOreValue.byMetadata(i)  ));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> list){
        if(this.isInCreativeTab(itemIn)) {
            for (EnumOreValue value : EnumOreValue.values()) {
                list.add(new ItemStack(this, 1, value.getMetadata()));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return super.getUnlocalizedName().replaceFirst("dust", EnumOreValue.dustNameByMetadata(stack.getMetadata())) + "dust";
    }
}
