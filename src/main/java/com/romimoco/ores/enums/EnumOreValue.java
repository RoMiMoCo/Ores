package com.romimoco.ores.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumOreValue implements IStringSerializable{

    POOR(4, "poor"),
        LOW(3, "low"),
        MODERATE(2, "moderate"),
        HIGH(1, "high"),
        RICH(0, "rich");


        private final int meta;
        private final String name;
        private static final EnumOreValue[] META_LOOKUP = new EnumOreValue[values().length];

        public int getMetadata(){
            return this.meta;
        }

        public String toString(){
            return this.name;
        }


        public static EnumOreValue byMetadata(int meta){
            if(meta < 1 || meta > META_LOOKUP.length){
                meta = 0;
            }
            return META_LOOKUP[meta];
        }

        public static String ingotNameByMetadata(int meta){
            switch(meta){
                case 4 : return "nugget";
                case 3 : return "shard";
                case 2 : return "chunk";
                case 1 : return "hunk";
                case 0 : return "ingot";
            }
            return "ingot";
        }

        public String getName(){
            return this.name;
        }

        private EnumOreValue(int meta, String name){
            this.meta = meta;
            this.name = name;
        }

        static{
            for (EnumOreValue value: values()){
                META_LOOKUP[value.getMetadata()] = value;
            }
        }
    }


