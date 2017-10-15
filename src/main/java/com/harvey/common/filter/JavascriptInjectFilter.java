package com.harvey.common.filter;

import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JavascriptInjectFilter extends AbstractExcludeUrlFilter {
    /**
     *
     */
    private static final long serialVersionUID = 8922282778633488492L;
    private static Logger logger = Logger.getLogger(JavascriptInjectFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            HttpServletRequest httprequest = (HttpServletRequest) request;
            String path = httprequest.getServletPath();
            if (path.contains("/upload/") && path.endsWith(".jsp")) {
                return;
            }
        }
        super.doFilter(request,response,filterChain);
    }

    private void doRealFilter(HttpServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        JavascriptInjectRequestWrapper requestWrapper = new JavascriptInjectRequestWrapper(request);
        try {
            filterChain.doFilter(requestWrapper, servletResponse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            getFilterConfig().getServletContext().log(e.getMessage());
            throw new IllegalArgumentException("error",e);
        }
    }

    @Override
    public void realFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        if(servletRequest instanceof HttpServletRequest){
            doRealFilter((HttpServletRequest)servletRequest, servletResponse, filterChain);
        }
    }

    public static class JavascriptInjectRequestWrapper extends HttpServletRequestWrapper {

        public JavascriptInjectRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        public Map getParameterMap() {
            return new JavascriptInjectParameterMap(super.getParameterMap());
        }

        public String[] getParameterValues(String s) {
            String[] orgValues = super.getParameterValues(s);
            if (orgValues != null) {
                String[] changeValues = new String[orgValues.length];
                for (int i = 0; i < orgValues.length; i++) {
                    changeValues[i] = HtmlUtils.htmlEscape(orgValues[i]);
                }
                return changeValues;
            }
            return orgValues;
        }

        @Override
        public String getParameter(String str) {
            return HtmlUtils.htmlEscape(super.getParameter(str));
        }
    }

    public static class JavascriptInjectParameterMap implements Map {
        private Map parameterMap = null;

        public JavascriptInjectParameterMap(Map parameterMap) {
            this.parameterMap = parameterMap;
        }

        @Override
        public int size() {
            return parameterMap.size();
        }

        @Override
        public boolean isEmpty() {
            return parameterMap.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return parameterMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return parameterMap.containsValue(value);
        }

        public Object get(Object key) {
            String[] orgValues = (String[]) parameterMap.get(key);
            if (orgValues != null) {
                String[] changeValues = new String[orgValues.length];
                for (int i = 0; i < orgValues.length; i++) {
                    changeValues[i] = HtmlUtils.htmlEscape(orgValues[i]);
                }
                return changeValues;
            }
            return orgValues;
        }

        @Override
        public Object put(Object key, Object value) {
            return parameterMap.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return parameterMap.remove(key);
        }

        @Override
        public void putAll(Map m) {
            parameterMap.putAll(m);
        }

        @Override
        public void clear() {
            parameterMap.clear();
        }

        @Override
        public Set keySet() {
            return parameterMap.keySet();
        }

        @Override
        public Collection values() {
            return parameterMap.values();
        }

        @Override
        public Set entrySet() {
            Set newEntrySet = new HashSet();
            Set<Entry> orgEntrySet = parameterMap.entrySet();
            for (Entry entry : orgEntrySet) {
                newEntrySet.add(new JavascriptInjectEntry(entry));
            }
            return newEntrySet;
        }

        @Override
        public boolean equals(Object o) {
            return parameterMap.equals(o);
        }

        @Override
        public int hashCode() {
            return parameterMap.hashCode();
        }
    }

    public static class JavascriptInjectEntry implements Map.Entry {
        private Map.Entry entry = null;

        public JavascriptInjectEntry(Map.Entry entry) {
            this.entry = entry;
        }

        @Override
        public Object getKey() {
            return HtmlUtils.htmlEscape((String) entry.getKey());
        }

        @Override
        public Object getValue() {
            String[] orgValues = (String[]) entry.getValue();
            if (orgValues != null) {
                String[] changeValues = new String[orgValues.length];
                for (int i = 0; i < orgValues.length; i++) {
                    changeValues[i] = HtmlUtils.htmlEscape(orgValues[i]);
                }
                return changeValues;
            }
            return orgValues;
        }

        @Override
        public Object setValue(Object value) {
            return entry.setValue(value);
        }
    }
}