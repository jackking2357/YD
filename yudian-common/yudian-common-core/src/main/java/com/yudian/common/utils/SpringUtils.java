package com.yudian.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;


@Component
public final class SpringUtils extends SpringUtil {

    
    public static boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isSingleton(name);
    }

    
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getType(name);
    }

    
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getAliases(name);
    }

    
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

}
