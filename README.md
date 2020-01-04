# Ores

The easiest* way to add custom ores to Minecraft 1.12.  Simply create an oreDefinitions.json file in the config/Romimoco/ores directory, define ores within and they will be automatically generated.

oreDefinitions.json has the following format.
```json
{
  "OreList": [
    {
      "Name" : "Iron",
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
      "Name" : "Сopper",
      "Color": "0xCC0000",
      "Hardness":"2.5",
      "Harvestlevel":"2",
      "genIngots": true,
      "genBuckets": true,
      "genVariants": true,
      "Generation":{
        "Dimensions": [
          {
            "ID": 0,
            "MinY": 1,
            "MaxY": 40,
            "SpawnChance": 30,
            "VeinSize": 4
          },
          {
            "ID": 1,
            "MinY": 0,
            "MaxY": 255,
            "SpawnChance": 30,
            "VeinSize": 4
          },
          {
            "ID": -1,
            "MinY": 0,
            "MaxY": 255,
            "SpawnChance": 40,
            "VeinSize": 4
          }
       ]
     }
    }
  ],
 "GemList":[
    {
      "Name" : "Diamond",
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
      "Name" : "Ruby",
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

Example for custom dimension
> The example will generate sulfur and tin ores which drops IC2 Sulfur dust and the tin ingot in Overworld and TwilightForest dimensions
```json
{
  "OreList": [
    {
      "Name" : "Tin",
      "Color": "0x9AFEFF",
      "Hardness":"2",
      "Harvestlevel":"1",
      "genIngots": true,
      "Generation":{
        "Dimensions": [
          {
            "ID": 0,
            "MinY": 1,
            "MaxY": 40,
            "SpawnChance": 50,
            "VeinSize": 4
          },
          {
            "ID": 7,
            "LikeID": 0,
            "MinY": 1,
            "MaxY": 64,
            "SpawnChance": 60,
            "VeinSize": 4
          }
        ]
      }
    }
  ],
  "GemList":[
    {
      "Name" : "Sulfur",
      "Color": "0xfffc00",
      "Hardness":"2.2",
      "Harvestlevel":"1",
      "Drops":"ic2:dust/16",
      "genVariants": true,
      "Generation":{
        "Dimensions": [
          {
            "ID": 0,
            "MinY": 1,
            "MaxY": 40,
            "SpawnChance": 30,
            "VeinSize": 4
          },
          {
            "ID": 7,
            "LikeID": 0,
            "MinY": 1,
            "MaxY": 40,
            "SpawnChance": 30,
            "VeinSize": 4
          }
        ]
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

Gems:
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
