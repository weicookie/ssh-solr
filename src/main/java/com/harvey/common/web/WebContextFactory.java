package com.harvey.common.web;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * IWebContext 工厂实现
 */
public class WebContextFactory implements ServletContextAware{

    private static ServletContext servletContext;
    private static String webRealPath;    //本地web根路径

    private WebContextFactory(){

    }

    private static ThreadLocal<IWebContext> ctxStore = new ThreadLocal<IWebContext>();

    public static void setWebContext(IWebContext ctx) {
        ctxStore.set(ctx);
    }

    public static IWebContext getWebContext() {
        IWebContext ctx =  ctxStore.get();
        if (ctx == null) {
            ctx = new DefaultWebContext();
            setWebContext(ctx);
        }
        return  ctxStore.get();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        setContext(servletContext);
    }

    public static synchronized void setContext(ServletContext servletContext){
        WebContextFactory.servletContext = servletContext;
    }

    public static String getWebRealPath() {
        if (webRealPath != null) {
            return webRealPath;
        } else {
            webRealPath =servletContext==null?null: servletContext.getRealPath("/");
            return webRealPath;
        }
    }

    public static String getContentPath() {
        return servletContext.getContextPath();
    }

    public static synchronized void setWebRootPath(String webRootPath) {
        WebContextFactory.webRealPath = webRootPath;
    }
    public static void setApplicationAttr(String key,String value) {
        WebContextFactory.servletContext.setAttribute(key, value);

    }
    public static String  getApplicationAttr(String key) {
        return WebContextFactory.servletContext.getAttribute(key).toString();
    }
}
