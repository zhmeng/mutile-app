package com.teamclub.routes.exceptions;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class ActionNotFoundException extends Exception {
    private String action = null;
    private Throwable t = null;
    private String message = null;

    public ActionNotFoundException(String action, Throwable t) {
        super(String.format("Action %s not found", action), t);
        this.action = action;
        this.t = t;
    }
    public ActionNotFoundException(String action, String message) {
        super(String.format("Action %s not found", action));
        this.action = action;
        this.message = message;
    }
    public String getErrorDescription() {
        String causeMsg = null;
        if(t != null && t instanceof ClassNotFoundException) {
            causeMsg = "ClassNotFound: " + t.getMessage();
        }else {
            causeMsg = t.getMessage();
        }
        return String.format("Action <strong>%s</strong> could not be found. Error raised is <strong>%s</strong>",
                action, causeMsg);
    }
}
