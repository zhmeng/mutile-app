package com.teamclub.routes.support;

/**
 * Created by ilkkzm on 17-4-19.
 */

import com.teamclub.routes.HTTPRequestAdapter;
import com.teamclub.routes.Router;
import com.teamclub.routes.exceptions.ActionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class RouterHandlerResolver {
    private static Logger logger = LoggerFactory.getLogger(RouterHandlerResolver.class);
    private Map<String, Object> cachedControllers = new LinkedHashMap<String, Object>();
    private Map<String, HandlerMethod> cachedHandlers = new LinkedHashMap<String, HandlerMethod>();

    public void setCachedControllers(Map<String, Object> controllers) {
        for(String key : controllers.keySet()) {
            this.cachedControllers.put(key.toLowerCase(), controllers.get(key));
        }
    }

    public HandlerMethod resolveHandler(Router.Route route, String fullAction, HTTPRequestAdapter req) throws ActionNotFoundException {
        HandlerMethod handlerMethod = null;
        if (this.cachedHandlers.containsKey(fullAction))
            handlerMethod = this.cachedHandlers.get(fullAction);
        else {
            handlerMethod = this.doResolveHandler(route, fullAction);
            this.cachedHandlers.put(fullAction, handlerMethod);
        }

        return handlerMethod;
    }

    private HandlerMethod doResolveHandler(Router.Route route, String fullAction) throws ActionNotFoundException {
        Method actionMethod;
        Object controllerObject;
        String controller = fullAction.substring(0, fullAction.lastIndexOf(".")).toLowerCase();
        String action = fullAction.substring(fullAction.lastIndexOf(".") + 1);
        controllerObject = cachedControllers.get(controller);
        if (controllerObject == null) {
            logger.debug("Did not find handler {} for [{} {}]", controller, route.method, route.path);
            throw new ActionNotFoundException(fullAction, new Exception(String.format("Controller %s not found", controller)));
        }
        actionMethod = findActionMethod(action, controllerObject);
        if (actionMethod == null) {
            logger.debug("Did not find handler method {}.{} for [{} {}]", controller, action, route.method, route.path);
            throw new ActionNotFoundException(fullAction,
                    new Exception(String.format("No method public static void %s was found in class $controller", action)));
        }

        return new RouterHandler(controllerObject, actionMethod, route);
    }

    private Method findActionMethod(String name, Object controller) {
        Class<?> targetClass = AopUtils.getTargetClass(controller);
        while (!"java.lang.Object".equals(targetClass.getName())) {
            for (Method method : targetClass.getDeclaredMethods())
                if (method.getName().equals(name) && Modifier.isPublic(method.getModifiers()))
                    return BridgeMethodResolver.findBridgedMethod(method);

            targetClass = targetClass.getSuperclass();
        }
        return null;
    }
}