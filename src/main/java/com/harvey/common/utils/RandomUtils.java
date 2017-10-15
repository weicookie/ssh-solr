package com.harvey.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成随机数
 */
public class RandomUtils {
    private static int seed = 0;
    private static final String DATE_PATTERN="yyMMddHHmmssSSS";
    public synchronized static String genDateAlis(int length) {
        if (seed < 1000) {
            seed++;
        } else {
            seed = 1;
        }
        String dateString = (new SimpleDateFormat(DATE_PATTERN)).format(UtilDateTime.addMilliseconds(new Date(), seed));
        if(length>15){
            dateString = StringUtils.leftPad(dateString,length,"0");
        }else {
            dateString = dateString.substring(15-length,15);
        }
        return dateString;
    }
}
