package org.c191239.customitemmerge;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ConfigManager {
    public double mergeX = 5.0;
    public double mergeY = 0.0;
    public double mergeZ = 5.0;
    private Path configFolder;
    private File configFile;
    public ConfigManager(){
        configFolder = FabricLoader.getInstance().getConfigDir();
        configFile = configFolder.resolve("c191239").resolve("custom-item-merge.txt").toFile();
    }
    public void loadConfigElseDefault() throws IOException {
        BufferedReader reader = null;
        if (configFile.exists()){
            try {
                reader = new BufferedReader(new FileReader(configFile));
                String s;
                while ((s = reader.readLine()) != null){
                    String[] args = s.split(":");
                    try {
                        if (args[0].equalsIgnoreCase("mergeX")){
                            this.mergeX = Double.parseDouble(args[1]);
                        } else if (args[0].equalsIgnoreCase("mergeY")) {
                            this.mergeY = Double.parseDouble(args[1]);
                        } else if (args[0].equalsIgnoreCase("mergeZ")) {
                            this.mergeZ = Double.parseDouble(args[1]);
                        }
                    } catch (Exception e){
                        CustomItemMerge.infoLog("Ignoring config: "+args[0]);
                    }
                }
            } finally {
                if (reader != null){
                    reader.close();
                }
            }
        } else {
            this.saveConfig();
        }
    }
    public void saveConfig() throws IOException {
        File myConfigFolder = configFolder.resolve("c191239").toFile();
        if (!myConfigFolder.exists()){
            myConfigFolder.mkdir();
        }
        configFile.createNewFile();
        PrintWriter writer = new PrintWriter(new FileWriter(configFile));
        writer.println("mergeX:"+this.mergeX);
        writer.println("mergeY:"+this.mergeY);
        writer.println("mergeZ:"+this.mergeZ);
        writer.close();
    }
}
