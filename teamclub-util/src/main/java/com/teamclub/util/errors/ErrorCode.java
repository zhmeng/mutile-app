package com.teamclub.util.errors;

/**
 * Created by ilkkzm on 17-4-27.
 */
public class ErrorCode {
    public Integer status;
    public String message;
    public ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
