package com.teamclub.util.libs;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.teamclub.util.errors.ErrorCode;

import java.util.Map;

/**
 * Created by ilkkzm on 17-4-27.
 */
public class Eithers {
    public static JsonNode toJson(F.Either<?, ErrorCode> ei) {
        if(ei.left.isDefined()) {
            if(ei.left.get() instanceof ErrorCode) {
                return Json.toJson(ei.left.get());
            } else {
                Map<String, Object> map = Maps.newHashMap();
                map.put("status", 200);
                map.put("data", ei.left.get());
                return Json.toJson(map);
            }
        } else {
            ErrorCode errorCode = ei.right.get();
            return Json.toJson(ei.right.get());
        }
    }
}
