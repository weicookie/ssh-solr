package com.harvey.common.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对session，request,application的attribute进行修改
 *
 */
public interface IWebContext {
    HttpServletRequest getRequest();
    HttpServletResponse getResponse();
    void setSessionAttr(String key, Object value);
    Object getSessionAttr(String key);
    void removeSessionAttr(String key);

    void setRequestAttr(String key, Object value);
    Object getRequestAttr(String key);
    void removeRequestAttr(String key);
    String getHost();
    String getRemoteAddr();

    Cookie getCookie(String cookieName);
    String getCookieValue(String cookieName);
    Cookie[] getCookies();
    void setCookie(Cookie c);
    void expireCookie(String cookieName);
}
