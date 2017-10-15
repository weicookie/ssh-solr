package com.harvey.common.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * json与对象的序列化
 */
public class JsonBinder {
    private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);

    private ObjectMapper mapper;
    private static JsonBinder normalBinder;
    private static JsonBinder nonNullBinder;

    private JsonBinder(JsonSerialize.Inclusion inclusion) {
        mapper = new ObjectMapper();
        //设置输出时包含属性的风格
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.getDeserializationConfig().set(
                org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 创建输出全部属性到Json字符串的Binder.
     */
    public static synchronized  JsonBinder buildNormalBinder() {
        if(normalBinder==null){
            normalBinder = new JsonBinder(JsonSerialize.Inclusion.ALWAYS);
        }
        return normalBinder;
    }

    /**
     * 创建只输出非空属性到Json字符串的Binder.
     */
    public static synchronized  JsonBinder buildNonNullBinder() {
        if(nonNullBinder==null){
            nonNullBinder = new JsonBinder(JsonSerialize.Inclusion.NON_NULL);
        }
        return nonNullBinder;
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * 如果需要返回数组，使用数据类型如Product[].class
     * <p/>
     */
    public <T> T toBean(String jsonString, Class<T> clazz) {
        if (null == jsonString || "" == jsonString) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果对象为Null,返回"null".
     * 如果集合为空集合,返回"[]".
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
