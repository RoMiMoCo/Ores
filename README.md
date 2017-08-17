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
  ]
}
```

By default Ore, Ingot, Block, Tools and Armor will be generated for each ore defined.  They will also be registered into the OreDictionary so as to be useable by other mods (they will be registered based on the Name attribute, oreXXXXX, ingotXXXXX et cetera).

Armor and Tools will get their attributes from the Hardness value, with a value of 3 being equivalent to vanilla iron.
Each tool can harvest one level above it's materials harvest level.

If genVariants is enabled in the config, 5 variants will be generated for each ore, ranging from poor to rich, with rich ores being equivalent to vanilla ores.
These variants will smelt into their own variant items, and those items can be combined to the next higher value variant or broken into the next lower variant.


**may not actually be the easiest, but hey, it's catchy*
