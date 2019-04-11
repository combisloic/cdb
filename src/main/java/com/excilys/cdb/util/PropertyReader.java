package com.excilys.cdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyReader {

    /**
     * prop Properties.
     */
    Properties prop;

    /**
     * LOGGER Logger.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(PropertyReader.class);

    /**
     * Constructor.
     */
    public PropertyReader() {
        prop = new Properties();

        try {
            InputStream inputStream = PropertyReader.class.getResourceAsStream("/application.properties");
            prop.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * Returns the value of the specified property.
     *
     * @param key String
     * @return String
     */
    public String get(String key) {
        return prop.getProperty(key);
    }
}
