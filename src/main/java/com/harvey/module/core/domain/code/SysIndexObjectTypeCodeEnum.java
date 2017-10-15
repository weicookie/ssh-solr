package com.harvey.module.core.domain.code;

/**
 * 索引类型枚举类
 */
public enum SysIndexObjectTypeCodeEnum implements ICodeEnum {
    /**
     * 用户 0
     */
    SYSUSER;

    /**
     * public static SysIndexObjectTypeCodeEnum[] values() //返回一个数组
     * @param toCode
     * @return
     */
    public static SysIndexObjectTypeCodeEnum fromCode(String toCode){
        return values()[Integer.parseInt(toCode)];
    }

    @Override
    public String toCode() {
        return Integer.toString(this.ordinal());
    }
}
