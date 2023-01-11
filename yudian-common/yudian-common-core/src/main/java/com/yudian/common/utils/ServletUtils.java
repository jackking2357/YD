package com.yudian.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletUtils extends ServletUtil {

    
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    
    public static Boolean getParameterToBool(String name) {
        return Convert.toBool(getRequest().getParameter(name));
    }

    
    public static Boolean getParameterToBool(String name, Boolean defaultValue) {
        return Convert.toBool(getRequest().getParameter(name), defaultValue);
    }

    
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static boolean isAjaxRequest(HttpServletRequest request) {

        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.equalsAnyIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.equalsAnyIgnoreCase(ajax, "json", "xml")) {
            return true;
        }
        return false;
    }

    public static String getClientIP() {
        return getClientIP(getRequest());
    }

}
