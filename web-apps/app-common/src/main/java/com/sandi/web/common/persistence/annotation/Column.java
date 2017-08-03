package com.sandi.web.common.persistence.annotation;

import java.lang.annotation.*;

/**
 * Created by dizl on 2015/5/8.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String value() default "";

    boolean isColumn() default true;//属性是否对应表中的某一列，默认为是
}
