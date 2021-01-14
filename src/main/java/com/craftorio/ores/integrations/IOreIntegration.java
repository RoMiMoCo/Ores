package com.craftorio.ores.integrations;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IOreIntegration {

    public void preInit(FMLPreInitializationEvent event);

    public void Init(FMLInitializationEvent event);

    public void postInit(FMLPostInitializationEvent event);

    public void registerItems(RegistryEvent.Register<Item> event);
}
