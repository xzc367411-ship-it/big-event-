package com.example.demo.validation;
import com.example.demo.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * State注解的校验逻辑实现
 */
public class StateValidation implements ConstraintValidator<State, String> {

    /**
     * 校验逻辑
     * @param value 要校验的字段值
     * @param context 校验上下文
     * @return true=校验通过，false=校验失败
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 1. 空值直接返回false（不允许为空）
        if (value == null) {
            return false;
        }
        // 2. 判断值是否为允许的两个状态
        if (value.equals("已发布") || value.equals("草稿")) {
            return true;
        }
        // 3. 其他情况校验失败
        return false;
    }
}