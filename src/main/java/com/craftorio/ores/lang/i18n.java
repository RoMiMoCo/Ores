package com.craftorio.ores.lang;


import com.craftorio.ores.util.OreLogger;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;


public class i18n {

    private LinkedList<HashMap<String, String>> translations;
    private HashMap<String, String> currentTranslation;

    public i18n(FMLPreInitializationEvent event) {
        translations = new LinkedList<>();
        File[] files = new File(event.getModConfigurationDirectory().getAbsolutePath() + "/Craftorio/ores").listFiles();

        if (null != files) {
            for (File f : files) {
                if (f.getName().endsWith("lang")) {
                    this.loadFile(f);
                }
            }
        }
        if (translations.size() == 0) {
            OreLogger.info("Translation requested, but no .lang files found.  Please set genResourcePack=true and run the game at least once to generate the en_us.lang file");
        }

    }

    private void loadFile(File f) {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

            HashMap<String, String> lang = new HashMap<>();
            lang.put("language", f.getName().replace(".lang", ""));
            String readline = "";


            while ((readline = reader.readLine()) != null) {
                String[] kv = readline.split("=");
                String key = kv[0];
                String value = kv[1];

                lang.put(key, value);

            }

            this.translations.push(lang);
        } catch (IOException e) {
            OreLogger.error(e.toString());
        }

    }

    public HashMap<String, String> getTranslation()
    {
        //Edge case where no files have been created.
        if (translations.size() == 0) {
            return null;
        }

        String locale = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        String fallbackLocale = Minecraft.getMinecraft().getLanguageManager().getLanguage("en_us").getLanguageCode();

        if (null == currentTranslation) {
            OreLogger.info("Trying to to load translation for the '" + locale + "' locale");
            for (HashMap<String, String> trans : translations) {
                if (trans.get("language").equals(locale)) {
                    OreLogger.info("Loaded translation for the '" + locale + "' locale");
                    currentTranslation = trans;
                    break;
                }
            }
        }

        if (null == currentTranslation) {
            OreLogger.info("Can't load user locale, fallback to '" + fallbackLocale + "'");
            OreLogger.info("Trying to to load fallback locale (" + fallbackLocale + ")");
            for (HashMap<String, String> trans : translations) {
                if (trans.get("language").equals(fallbackLocale)) {
                    OreLogger.info("Loaded translation for the '" + fallbackLocale + "' locale");
                    currentTranslation = trans;
                    break;
                }
            }
        }

        if (null == currentTranslation) {
            OreLogger.info("Can't load any translation");
        }

        return currentTranslation;
    }

    @SideOnly(Side.CLIENT)
    public String translate(String s) {
        HashMap<String, String> trans = getTranslation();

        if (null != trans) {
            String result = trans.get(s);
            if (!result.equals("")) {
                s = result;
            }
        }

        return s;
    }
}
