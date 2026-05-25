package com.example.demo.anno;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.demo.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

// 元注解：文档中会保留这个注解
@Documented
// 元注解：这个注解只能用在 字段(FIELD) 上
@Target({ FIELD })
// 元注解：这个注解的生命周期是 运行时
@Retention(RUNTIME)
// 指定提供校验规则的类
@Constraint(validatedBy = { StateValidation.class })
public @interface State {

    // 校验失败后的提示信息
    String message() default "state参数的值只能是已发布或者草稿";

    // 校验分组（用于分组校验场景）
    Class<?>[] groups() default { };

    // 负载：获取到State注解的附加信息（扩展用）
    Class<? extends Payload>[] payload() default { };
}