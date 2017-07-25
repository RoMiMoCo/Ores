package com.romimoco.ores.events;

import com.romimoco.ores.util.OreConfig;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OreGenEventHandler {

        @SubscribeEvent(priority = EventPriority.NORMAL)
        public void vanillaGenerationAttempt(OreGenEvent.GenerateMinable event){
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.IRON) && OreConfig.worldgen.disableVanillaIron){
                event.setResult(Event.Result.DENY);
            }
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.COAL) && OreConfig.worldgen.disableVanillaCoal){
                event.setResult(Event.Result.DENY);
            }
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.GOLD) && OreConfig.worldgen.disableVanillaGold){
                event.setResult(Event.Result.DENY);
            }
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.REDSTONE) && OreConfig.worldgen.disableVanillaRedstone){
                event.setResult(Event.Result.DENY);
            }
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.DIAMOND) && OreConfig.worldgen.disableVanillaDiamond){
                event.setResult(Event.Result.DENY);
            }
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.EMERALD) && OreConfig.worldgen.disableVanillaEmerald){
                event.setResult(Event.Result.DENY);
            }
            if(event.getType().equals(OreGenEvent.GenerateMinable.EventType.QUARTZ) && OreConfig.worldgen.disableVanillaQuartz){
                event.setResult(Event.Result.DENY);
            }
        }

}
