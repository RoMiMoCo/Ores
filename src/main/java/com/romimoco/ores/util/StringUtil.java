package com.romimoco.ores.util;

public class StringUtil {

   public static String toSentenceCase(String in){
       return in.substring(0,1).toUpperCase() + in.substring(1);
   }

}
