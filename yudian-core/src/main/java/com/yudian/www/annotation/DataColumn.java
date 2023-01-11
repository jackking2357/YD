package com.yudian.www.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataColumn {

    
    String key();

    
    String value();

}
