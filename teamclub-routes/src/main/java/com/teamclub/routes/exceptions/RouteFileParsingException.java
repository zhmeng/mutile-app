package com.teamclub.routes.exceptions;

import org.springframework.beans.BeansException;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class RouteFileParsingException extends BeansException {
    public RouteFileParsingException(String msg) {
        super(msg);
    }
    public RouteFileParsingException(String msg, Throwable t) {
        super(msg, t);
    }
}
