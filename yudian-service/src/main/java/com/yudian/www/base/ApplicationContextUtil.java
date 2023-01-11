package com.yudian.www.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 根据bean名称获取指定类型bean
     *
     * @param beanName bean名称
     * @param clazz    返回的bean类型,若类型不匹配,将抛出异常
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return getApplicationContext().getBean(beanName, clazz);
    }
}
