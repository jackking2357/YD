package com.yudian.www.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    DataColumn[] value();

}
