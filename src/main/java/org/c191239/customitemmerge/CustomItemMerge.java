package org.c191239.customitemmerge;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CustomItemMerge implements ModInitializer {
    private static Logger LOGGER = LogManager.getLogger();
    public static ConfigManager configManager;
    @Override
    public void onInitialize() {
        configManager = new ConfigManager();
        try {
            configManager.loadConfigElseDefault();
        } catch (IOException e){
            CustomItemMerge.errorLog("Suppressed exception", e);
        }
        CustomItemMerge.infoLog("Using custom-item-merge by C191239");
    }
    public static void errorLog(String s, Throwable t){
        LOGGER.error("[Custom item merge] "+s, t);
    }
    public static void infoLog(String s){
        LOGGER.info("[Custom item merge] "+s);
    }
}
