
package com.alibaba.cloud.ai.agentdatamanager.annotation;

import com.alibaba.cloud.ai.agentdatamanager.annotation.InEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InEnumValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface InEnum {

	// 指定枚举类
	Class<? extends Enum<?>> value();

	// 指定判断的方法，默认是 name()，也可以是 getCode() 等
	String method() default "name";

	String message() default "变量值必须是指定枚举值之一";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
