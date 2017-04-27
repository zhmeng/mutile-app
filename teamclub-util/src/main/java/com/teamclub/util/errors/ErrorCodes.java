package com.teamclub.util.errors;

/**
 * Created by ilkkzm on 17-4-27.
 */
public class ErrorCodes {
    public static ErrorCode of(String msg) {
        return new ErrorCode(555, msg);
    }
    public static ErrorCode SUCCESS = new ErrorCode(200, "SUCCESS");
    public static ErrorCode BAD_REQUEST = new ErrorCode(500, "请求失败");
    public static ErrorCode BIZ_FAILURE = new ErrorCode(501, "业务失败");

    public static ErrorCode ENTITY_NOT_FOUND = new ErrorCode(611, "实体不存在");
}
