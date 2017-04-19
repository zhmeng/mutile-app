package com.teamclub.routes.exceptions;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class NoRouteFoundException  extends RuntimeException {
    public String method;
    public String path;
    public NoRouteFoundException(String method, String path) {
        super("No route Found");
        this.method = method;
        this.path = path;
    }

    public String toString() {
        return this.getMessage() + " method[ " + this.method + " ]path[ " + this.path + " ]";
    }
}
