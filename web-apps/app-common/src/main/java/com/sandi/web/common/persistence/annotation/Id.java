package com.sandi.web.common.persistence.annotation;

import java.lang.annotation.*;

/**
 * Created by dizl on 2015/5/8.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
    boolean isAutoKey() default false;
}
