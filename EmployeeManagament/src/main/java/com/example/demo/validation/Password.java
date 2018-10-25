package com.example.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
public @interface Password {
  String message() default "{Password}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}