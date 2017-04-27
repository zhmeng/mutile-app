package com.teamclub.ebean;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by ilkkzm on 17-4-21.
 */
@Component
public class EbeanFactoryBean implements FactoryBean<EbeanServer> {

    @Autowired
    CurrentUser currentUser;

    @Override
    public EbeanServer getObject() throws Exception {
        ServerConfig config = new ServerConfig();
        config.setName("db");
        config.setCurrentUserProvider(currentUser);
        config.loadFromProperties();
        config.setDefaultServer(true);
        config.setPackages(Arrays.asList("com.teamclub.domain"));
        config.setRegister(true);
        return EbeanServerFactory.create(config);
    }

    @Override
    public Class<?> getObjectType() {
        return EbeanServer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
