package com.teamclub.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by ilkkzm on 17-4-25.
 */
@Component
public class Springs implements ApplicationContextAware{
    private ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    public ApplicationContext context() {
        return this.context;
    }
    public <T> T getBean(Class<T> c) throws BeansException {
        return context().getBean(c);
    }

    public Object getBean(String var1) throws BeansException {
        return context().getBean(var1);
    }

    public <T> T getBean(String var1, Class<T> var2) throws BeansException {
        return context().getBean(var1, var2);
    }

    public Object getBean(String var1, Object... var2) throws BeansException {
        return context().getBean(var1, var2);
    }

    public <T> T getBean(Class<T> var1, Object... var2) throws BeansException {
        return context().getBean(var1, var2);
    }
}
