package com.sandi.web.common.persistence.annotation;

import java.lang.annotation.*;

/**
 * Created by dizl on 2015/5/8.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entity {
    Class clazz();//对应的类型

    String attr() default "";//关联属性,默认为当前表的主键

    String relAttr();//对应的关联属性

    String[] cond() default {};//

    boolean autoLoad() default false;//默认为非自动加载

    boolean autoDel() default false;//默认不能自动删除

    boolean autoSave() default false;//默认不能自动保存或修改
}