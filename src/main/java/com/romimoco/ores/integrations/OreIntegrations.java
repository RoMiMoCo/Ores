package com.romimoco.ores.integrations;

import java.util.LinkedList;

public class OreIntegrations {

    public static LinkedList<IOreIntegration> integrations = new LinkedList<>();

    public static void addIntegration(IOreIntegration in){
       integrations.push(in);
    }
}
