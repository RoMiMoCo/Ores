package com.romimoco.ores.lang;


import com.romimoco.ores.util.OreLogger;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class i18n {

    private LinkedList<HashMap<String, String>> translations;

    public i18n(){
        translations = new LinkedList<>();
        File[] files = new File("config/Romimoco/ores").listFiles();

        for(File f: files ){
            if(f.getName().endsWith("lang")){
                this.loadFile(f);
            }
        }

    }

    private void loadFile(File f) {
        try{

           BufferedReader reader = new BufferedReader(new FileReader(f));

           HashMap<String, String> lang = new HashMap<>();
           lang.put("language", f.getName().replace(".lang", ""));
           String readline= "";


           while((readline = reader.readLine()) != null){
               String[] kv = readline.split("=");
               String key = kv[0];
               String value = kv[1];

               lang.put(key, value);

           }

           this.translations.push(lang);
       }catch(IOException e){
           OreLogger.error(e.toString());
       }

    }

    public String translate(String s){
        String locale = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        int i = 0;
        for(HashMap<String, String> lang : translations){
            if(lang.get("language").equals(locale)){
                if(i > 0){
                    translations.push(translations.remove(i)); //move this to the head of the list to make future lookups faster
                }
                return lang.get(s);
            }
            i++;
        }
        return s;
    }
}
