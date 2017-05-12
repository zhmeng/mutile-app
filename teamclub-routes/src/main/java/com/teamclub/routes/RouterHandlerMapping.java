package com.teamclub.routes;

import com.teamclub.routes.exceptions.NoRouteFoundException;
import com.teamclub.routes.exceptions.RouteFileParsingException;
import com.teamclub.routes.support.RouterHandlerResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class RouterHandlerMapping extends AbstractHandlerMapping {
    public static Logger logger = LoggerFactory.getLogger(RouterHandlerMapping.class);

    public List<String> routeFiles = null;
    public Boolean isAutoReloadEnabled = false;
    private RouterHandlerResolver methodResolver;
    public RouterHandlerMapping() {
        this.methodResolver = new RouterHandlerResolver();
    }

    public void reloadRoutesConfiguration() {
        List<Resource> fileResources = new ArrayList<Resource>();
        try {
            for(String fileName : routeFiles) {
                fileResources.addAll(Arrays.asList(getApplicationContext().getResources(fileName)));
            }
            Router.detectChanges(fileResources);
        } catch (IOException e) {
            throw new RouteFileParsingException("Could not read route configuration files", e);
        }
    }

    @Override
    protected void initApplicationContext() throws BeansException {
        super.initApplicationContext();
        this.methodResolver.setCachedControllers(getApplicationContext().getBeansWithAnnotation(RestController.class));
        this.methodResolver.setCachedControllers(getApplicationContext().getBeansWithAnnotation(Controller.class));
        List<Resource> fileResources = new ArrayList<Resource>();
        try {
            for(String fileName : routeFiles) {
                fileResources.addAll(Arrays.asList(getApplicationContext().getResources(fileName)));
            }
            Router.load(fileResources);
        } catch (IOException e) {
            throw new RouteFileParsingException("Could not read route configuration files", e);
        }
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest httpServletRequest) throws Exception {
        HandlerMethod handler;
        if(this.isAutoReloadEnabled) {
            this.reloadRoutesConfiguration();
        }
        try {
            HTTPRequestAdapter httpRequestAdapter = HTTPRequestAdapter.parseRequest(httpServletRequest);
            Router.Route route = Router.route(httpRequestAdapter);
            logger.debug(String.format("Looking up handler method for path %s (%s %s %s)", route.path, route.method, route.path, route.action));
            handler = this.methodResolver.resolveHandler(route, httpRequestAdapter.action, httpRequestAdapter);
            httpServletRequest.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, httpRequestAdapter.routeArgs);
            httpServletRequest.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, route.pattern.toString());
        } catch (NoRouteFoundException nrfe) {
            handler = null;
            logger.warn("no route found for method[" + nrfe.method + "] and path[" + nrfe.path + "]");
        }
        return handler;
    }

    public List<String> getRouteFiles() {
        return routeFiles;
    }

    public void setRouteFiles(List<String> routeFiles) {
        this.routeFiles = routeFiles;
    }

    public Boolean getAutoReloadEnabled() {
        return isAutoReloadEnabled;
    }

    public void setAutoReloadEnabled(Boolean autoReloadEnabled) {
        isAutoReloadEnabled = autoReloadEnabled;
    }
}
