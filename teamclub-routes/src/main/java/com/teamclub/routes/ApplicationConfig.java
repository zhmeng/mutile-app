package com.teamclub.routes;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilkkzm on 17-4-19.
 */
@Configuration
public class ApplicationConfig extends RouterConfigurationSupport {
    @Override
    public List<String> listRouteFiles () {
        try {
            List<String> routeFiles = new ArrayList<String>();
            routeFiles.add("classpath:*.routes");
            return routeFiles;
        }catch (Exception e) {
            return Lists.newArrayList();
        }

    }
}
