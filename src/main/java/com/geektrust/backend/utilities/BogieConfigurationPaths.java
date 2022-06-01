package com.geektrust.backend.utilities;

public enum BogieConfigurationPaths {

    BASEPATH("src/main/java/com/geektrust/backend/"), 
    //TRAINs_BOGIE_CONFIG_PATH(BASEPATH.getPath() + "JSONS/trainsBogiesConfigurations.json"), trainsBogiesConfigurations.json
    TRAINs_BOGIE_CONFIG_PATH("JSONS/trainsBogiesConfigurations.json"),
    TRAIN_A_BOGIE_CONFIG_PATH(BASEPATH.getPath() + "JSONS/train-A-BogiesConfigurations.json"),
    TRAIN_B_BOGIE_CONFIG_PATH(BASEPATH.getPath() + "JSONS/train-B-BogiesConfigurations.json");
    
    private String path_value;
    private BogieConfigurationPaths(String value)
    {
        this.path_value = value;
    }

    public String getPath()
    {
        return this.path_value;
    }
}
