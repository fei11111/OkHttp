package com.fei.okhttpdemo.retrofit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: POST
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:34
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMap {
    String value();
}
