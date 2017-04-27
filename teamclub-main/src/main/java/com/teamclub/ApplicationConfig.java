package com.teamclub;

import com.google.common.collect.Lists;
import com.teamclub.routes.RouterConfigurationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilkkzm on 17-4-19.
 */
@Configuration
public class ApplicationConfig extends RouterConfigurationSupport {
    private Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    @Override
    public List<String> listRouteFiles () {
        try {
            ClassLoader cl = ApplicationConfig.class.getClassLoader();
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath*:/*.routes");
            List<String> routeFiles = new ArrayList<String>();
            for(Resource resource: resources) {
                logger.info("add {} to route files", resource.getURI().getPath());
                routeFiles.add(resource.getURI().getPath());
            }
            return routeFiles;
        }catch (Exception e) {
            return Lists.newArrayList();
        }

    }
}
