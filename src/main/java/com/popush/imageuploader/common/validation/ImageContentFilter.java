package com.popush.imageuploader.common.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ImageContentFilterValidator.class)
public @interface ImageContentFilter {
    String message() default "image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
