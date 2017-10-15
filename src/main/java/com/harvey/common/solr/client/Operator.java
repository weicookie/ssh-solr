package com.harvey.common.solr.client;

/**
 * 查询条件关系枚举类：或者、并且.
 */
public enum Operator {
    OR(" OR "),
    AND(" AND ");
    private String operator;

    private Operator(String operator) {
        this.operator = operator;
    }
    public String toCode(){
        return operator;
    }
}
