package com.craftorio.ores.integrations;

import com.craftorio.ores.Items.ModItems;
import com.craftorio.ores.blocks.BaseOre;
import com.craftorio.ores.blocks.ModBlocks;
import com.craftorio.ores.enums.EnumOreValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class TiConIntegration implements IOreIntegration {


    public void preInit(FMLPreInitializationEvent event) {
        //Set up IMC Stuff here
    }

    public void Init(FMLInitializationEvent event) {


        for (BaseOre b : ModBlocks.ORES) {
            addMelting(b);
            addCasting(b);
        }
    }


    public void postInit(FMLPostInitializationEvent event) {

    }

    private void addMelting(BaseOre ore) {
        Fluid moltenOre = new ticonFluid(ore);

        if (!FluidRegistry.registerFluid(moltenOre)) { //if the fluid already exists, just use it
            moltenOre = FluidRegistry.getFluid(moltenOre.getName());
        }

        for (EnumOreValue value : EnumOreValue.oreValues(ore)) {
            int i = value.getVariant();
            int amt = i > 0 ? (int) Math.pow(2, 4 - i) * 18 : 288;
            if (ore.shouldRegister) {
                TinkerRegistry.registerMelting(new ItemStack(ore, 1, value.getMetadata()), moltenOre, amt);
            }
            TinkerRegistry.registerMelting(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, i), moltenOre, amt / 2);
            if (ore.genDusts) {
                TinkerRegistry.registerMelting(new ItemStack(ModItems.DUSTS.get(ore.name + "Dust"), 1, i), moltenOre, amt / 2);
            }
        }
    }


    private void addCasting(BaseOre ore) {
        if (ore.genVariants) {
            TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, 4), TinkerSmeltery.castNugget, FluidRegistry.getFluid(ore.name), 9);
        } else {
            TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, 4), TinkerSmeltery.castNugget, FluidRegistry.getFluid(ore.name), 16);
        }
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.INGOTS.get(ore.name + "Ingot"), 1, 0), TinkerSmeltery.castIngot, FluidRegistry.getFluid(ore.name), 144);
    }

    private class ticonFluid extends FluidMolten {

        public ticonFluid(BaseOre b) {
            super(b.name, b.getColor());
            this.setTemperature(800);
        }
    }

    public void registerItems(RegistryEvent.Register<Item> event)
    {

    }
}
