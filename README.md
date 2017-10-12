# Ores

The easiest* way to add custom ores to Minecraft 1.12.  Simply create an oreDefinitions.json file in the config/Romimoco/ores directory, define ores within and they will be automatically generated.

oreDefinitions.json has the following format.
```json
{
  "OreList": [
    {
      "Name" : "iron",
      "Color": "0xF3D1BB",
      "Hardness":"3",
      "Harvestlevel":"3",
      "Generation":{
        "MinY": 16,
        "MaxY": 64,
        "SpawnChance": 5,
        "VeinSize": 8
      }
    },
    {
      "Name" : "copper",
      "Color": "0xCC0000",
      "Hardness":"2.5",
      "Harvestlevel":"2",
      "Generation":{
        "MinY": 32,
        "MaxY": 64,
        "SpawnChance": 8,
        "VeinSize": 8
      }
    }
  ],
 "GemList":[
    {
      "Name" : "diamond",
      "Color": "0x1589ff",
      "Hardness":"2.2",
      "Harvestlevel":"4",
      "Drops":"minecraft:diamond",
      "Generation":{
        "MinY": 64,
        "MaxY": 96,
        "SpawnChance": 5,
        "VeinSize": 24
      }
    },
    {
      "Name" : "ruby",
      "Color": "0xff0000",
      "Hardness":"2.2",
      "Harvestlevel":"4",
      "Drops":{
          "Type":"gem",
          "Cut":"round"
      },
      "Generation":{
          "MinY": 64,
          "MaxY": 96,
          "SpawnChance": 5,
          "VeinSize": 24
      }
    }
  ]
}
```

Ores:
  * Drops itself when mined
  * Smelts into an ingot
  * Variants smelt into variant ingots
  * Will generate Ore, Ingots, Block, Tools, Armor, Dusts (based on config entries)

Gems: ( >= v0.2.0)
  * Drops an Item when mined
  * Unsmeltable (currently)
  * Drops can be custom defined or a string such as "minecraft:diamond" 
  * Will generate Ore, Drops (if custom defined)
  * If the Drop type is "gem", Tools, Armor, and Blocks will be generated.
  * Custom Drops can have a BurnTime, which is the number of ticks they will burn in a furnace for. 
  * The "cut" of a custom drop will define what it looks like.  Options are ball, chunk1, chunk2, crystal, rhomboid, round, square, teardrop, and trilliant.


Armor and Tools will get their attributes from the Hardness value, with a value of 3 being equivalent to vanilla iron.
Each tool can harvest one level above it's materials harvest level.

If genVariants is enabled in the config, 5 variants will be generated for each ore, ranging from poor to rich, with rich ores being equivalent to vanilla ores.
These variants will smelt into their own variant items (Gem variants will drop multiple items), and those items can be combined to the next higher value variant or broken into the next lower variant.


**may not actually be the easiest, but hey, it's catchy*
