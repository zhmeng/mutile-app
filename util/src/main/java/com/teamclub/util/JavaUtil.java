package com.teamclub.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ilkkzm on 17-4-18.
 */
public class JavaUtil {
    private static Logger logger = LoggerFactory.getLogger(JavaUtil.class);
    public static String show() {
        logger.info("JavaUtil");
        return "JavaUtil";
    }
}
