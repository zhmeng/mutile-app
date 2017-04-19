package com.teamclub.routes.exceptions;

import java.util.Map;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class NoHandlerFoundException extends RuntimeException {
    private String action;
    private Map<String, Object> args;
    public NoHandlerFoundException(String action, Map<String, Object> args) {
        super("No Handler Exception");
        this.action = action;
        this.args = args;
    }
    public String toString() {
        return this.getMessage() + ", action [" + this.action + "] args [" + this.args + "]";
    }
}
