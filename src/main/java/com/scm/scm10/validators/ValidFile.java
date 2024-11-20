package com.scm.scm10.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Filevalidator.class)
public @interface ValidFile {
    String message() default "Invalid file";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
