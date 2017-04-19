package com.teamclub.routes.support;

import com.teamclub.routes.Router;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class RouterHandler extends HandlerMethod {
    public Router.Route route = null;
    public RouterHandler(Object bean, Method method, Router.Route route) {
        super(bean, method);
        this.route = route;
    }
}
