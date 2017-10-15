package com.harvey.common.solr.client;

import com.harvey.common.utils.UtilDateTime;
import org.apache.commons.lang.StringUtils;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 建立一个快速生成solr查询条件的工具类.
 * 其实就是一个拼接查询条件的过程
 */
public class SolrQueryBuilder {
    private Operator operator = Operator.OR;//默认是OR的查询
    private List<Field> fields = new ArrayList<Field>();
    private List<SolrQueryBuilder> subFields = new ArrayList<SolrQueryBuilder>();
    private List<String> extQuery = new ArrayList<String>();
    //特殊字符
    private static final String[] specChars = new String[]{"+", "-", "&&", "||" ,"!" ,"(", ")", "{", "}", "[" ,"]" ,"^", "\"", "~", "*", "?" ,":", "/"};

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //1972-05-20T17:33:18.772Z
    private static final String MIN_DATE = "*";
    private static final String MAX_DATE = "*";
    private static final String TO = " TO ";

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public static SolrQueryBuilder start(){
        return new SolrQueryBuilder();
    }

    public static SolrQueryBuilder start(Operator operator){
        SolrQueryBuilder builder = new SolrQueryBuilder();
        builder.setOperator(operator);
        return builder;
    }

    public SolrQueryBuilder addQuery(String field, String value) {
        if (StringUtils.isBlank(field) || StringUtils.isBlank(value)) {
            return this;
        }
        fields.add(new Field(field, value));
        return this;
    }

    /**
     * 子查询
     * @param query
     * @return
     */
    public SolrQueryBuilder addSubQuery(SolrQueryBuilder query) {
        if (query != null) {
            subFields.add(query);
        }
        return this;
    }

    /**
     * 不等于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder notEquals(String field, String value) {
        if (StringUtils.isBlank(field) || StringUtils.isBlank(value)) {
            return this;
        }
        return addQuery(" NOT " + field, parseValue(value));
    }

    /**
     * 模糊查询 如 roam~0.8
     * @param field
     * @param value
     * @param relationLevel 相关度查询
     * @return
     */
    public SolrQueryBuilder like(String field, String value, Double relationLevel) {
        if (StringUtils.isBlank(field) || StringUtils.isBlank(value)) {
            return this;
        }
        String val =  parseValue(value) + " ~ ";
        if(null != relationLevel){
            val = val + relationLevel.doubleValue();
        }
        return addQuery(field, val);
    }

    /**
     * 小于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder lt(String field, Number value) {
        String rangeValue = "{ *" + TO + value + "}";
        return addQuery(field, rangeValue);
    }

    /**
     * 大于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder gt(String field, Number value) {
        String rangeValue = "{" +value + TO +   "* }";
        return addQuery(field, rangeValue);
    }

    /**
     * 小于等于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder le(String field, Number value) {
        String rangeValue = "[ *" + TO + value + "]";
        return addQuery(field, rangeValue);
    }

    /**
     * 大于等于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder ge(String field, Number value) {
        String rangeValue = "[" +value + TO +   "* ]";
        return addQuery(field, rangeValue);
    }

    /**
     * 在区间内
     * @param field
     * @param min
     * @param max
     * @param isInclude 是否包含最大最小值
     * @return
     */
    public SolrQueryBuilder between(String field, Number min,Number max,boolean isInclude){
        if(min==null || max==null){
            return this;
        }
        String rangeValue = "";
        if(isInclude){
            rangeValue = "[" + min + TO +  max + "]";
        }else{
            rangeValue = "{" + min + TO +  max + "}";
        }
        return addQuery(field, rangeValue);
    }

    /**
     * 小于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder lt(String field, Date value){
        if (value == null) {
            return this;
        }
        String maxValue = UtilDateTime.convertDateToString(UtilDateTime.addHours(value,-8), DATE_TIME_FORMAT);
        String rangeValue = "{" + MIN_DATE + TO + maxValue + "}";
        return addQuery(field, rangeValue);
    }

    /**
     * 大于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder gt(String field, Date value) {
        if (value == null) {
            return this;
        }
        String minValue = UtilDateTime.convertDateToString(UtilDateTime.addHours(value,-8), DATE_TIME_FORMAT);
        String rangeValue = "{" + minValue + TO + MAX_DATE + "}";
        return addQuery(field, rangeValue);
    }

    /**
     * 小于等于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder le(String field, Date value){
        if (value == null) {
            return this;
        }
        String maxValue = UtilDateTime.convertDateToString(UtilDateTime.addHours(value,-8), DATE_TIME_FORMAT);
        String rangeValue = "[" + MIN_DATE + TO + maxValue + "]";
        return addQuery(field, rangeValue);
    }

    /**
     * 大于等于
     * @param field
     * @param value
     * @return
     */
    public SolrQueryBuilder ge(String field, Date value) {
        if (value == null) {
            return this;
        }
        String minValue = UtilDateTime.convertDateToString(UtilDateTime.addHours(value,-8), DATE_TIME_FORMAT);
        String rangeValue = "[" + minValue + TO + MAX_DATE + "]";
        return addQuery(field, rangeValue);
    }

    /**
     * 在区间内
     * @param field
     * @param start
     * @param end
     * @param isInclude 是否包含开始结束时间
     * @return
     */
    public SolrQueryBuilder between(String field, Date start,Date end, boolean isInclude){
        if(start==null || end==null){
            return this;
        }
        String startValue = UtilDateTime.convertDateToString(UtilDateTime.addHours(start,-8), DATE_TIME_FORMAT);
        String endValue = UtilDateTime.convertDateToString(UtilDateTime.addHours(end,-8), DATE_TIME_FORMAT);
        String rangeValue = "";
        if(isInclude) {
            rangeValue = "[" + startValue + TO + endValue + "]";
        }else{
            rangeValue = "{" + startValue + TO + endValue + "}";
        }
        return addQuery(field,rangeValue);
    }



    private String parseValue(String value){
        for (String s : specChars){
            if(value.contains(s)){
                value.replace(s,"");
            }
        }
        return value;
    }

    private static class Field {
        private String field;
        private String value;

        private Field(String field, String value) {
            this.field = field;
            this.value = value;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            return builder.append(field).append(":").append(value).toString();
        }
    }

    public String getQueryString() {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            buff.append(fields.get(i).toString());
            if (i < fields.size() - 1) {
                buff.append(operator.toCode());
            }
        }

        for (SolrQueryBuilder queryBuilder : subFields) {
            String subQueryString = queryBuilder.getQueryString();
            if(StringUtils.isNotBlank(subQueryString)){
                //()子查询
                buff.append(operator.toCode()).append("(").append(subQueryString).append(")");
            }
        }

        if(fields.size()==0){
            String temp = buff.toString().replaceFirst(operator.toCode(),"");
            buff.setLength(0);
            buff.append(temp);
        }

        for (String query : extQuery) {
            if(StringUtils.isNotBlank(query)){
                buff.append(operator.toCode()).append("(").append(query).append(")");
            }
        }
        return buff.toString();
    }

    public String toString() {
        return getQueryString();
    }
}
