package com.harvey.common.interceptor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 渲染页面拦截器，controller还未完成渲染页面工作时
 */
public class SharedRenderVariableInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView == null) {
            return;
        }
        String viewName = modelAndView.getViewName();
        if(viewName != null && !viewName.startsWith("redirect:")) {
            modelAndView.addAllObjects(perRequest(request,response));
        }
    }

    protected Map<String,Object> perRequest(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String,Object> model = new HashMap<String,Object>();

        model.put("share_current_request_time", new Date());
        model.put("ctx", request.getContextPath());
        return model;
    }
}
