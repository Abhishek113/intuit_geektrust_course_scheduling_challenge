package com.geektrust.backend.utilities;

public enum BogieConfigurationPaths {

    TRAINs_BOGIE_CONFIG_PATH("JSONS/trainsBogiesConfigurations.json");
    
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
