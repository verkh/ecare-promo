package com.ecare.config;

import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
@Startup
@Getter
public class Configurations {

    private String serverAddress;

    @PostConstruct
    public void readSettings() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        this.serverAddress = properties.getProperty("ecare.main.server").toString();
    }
}
