package com.webtest.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigParser {

    private static ConfigParser parser = null;
    private static final String propertiesFile = "src/main/resources/config.properties";

    private String gecko_path;
    private String base_url;

    private ConfigParser() {
    }

    public static ConfigParser getConfig() {
        if (parser == null) {
            parser = new ConfigParser();
            Properties properties = loadProperties(propertiesFile);
            parser.setGecko_path(properties.getProperty("GECKO_PATH").toString());
            parser.setBase_url(properties.getProperty("REVOLUT_HOMEPAGE_URL").toString());
        }
        return parser;
    }

    private static Properties loadProperties(String file) {
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader(file);
            properties.load(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getGecko_path() {
        return gecko_path;
    }

    public void setGecko_path(String gecko_path) {
        this.gecko_path = gecko_path;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }
}
