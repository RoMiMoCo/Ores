package com.romimoco.ores.proxy;

import com.romimoco.ores.Items.ModItems;
import com.romimoco.ores.blocks.ModBlocks;
import com.romimoco.ores.util.OreColor;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.collection.parallel.ParIterableLike;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        //register item/blockColorHandlers
        OreColor colorHandler = new OreColor();
        for(Block B : ModBlocks.ORES) {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(colorHandler, B);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(colorHandler, B);
        }
        for(Block B : ModBlocks.BLOCKS.values()) {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(colorHandler, B);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(colorHandler, B);
        }
        for(Item i : ModItems.INGOTS.values()){
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(colorHandler, i);
        }
        for(Item i : ModItems.ARMORS.values()){
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(colorHandler, i);
        }
        for(Item i : ModItems.TOOLS.values()){
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(colorHandler, i);
        }
        for(Item i : ModItems.MISC.values()){
            //Minecraft.getMinecraft().getItemColors().registerItemColorHandler(colorHandler, i);
        }
        //    ModBlocks.postInit();
    }

}
