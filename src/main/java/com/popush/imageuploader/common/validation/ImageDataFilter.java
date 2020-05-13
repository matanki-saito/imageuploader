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
@Constraint(validatedBy = ImageDataFilterValidator.class)
public @interface ImageDataFilter {

    String message() default "image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Format name such as PNG, JPEG, GIF.
     * ImageIOで定義されているもの
     */
    String[] formatName() default "";

    /*
    単位はpixel
     */
    int minWidth() default 0;

    /*
    単位はpixel
     */
    int minHeight() default 0;

    /*
    単位はpixel
     */
    int maxWidth() default Integer.MAX_VALUE;

    /*
    単位はpixel
     */
    int maxHeight() default Integer.MAX_VALUE;

    /*
    単位はbyte　デフォルトは3MB
     */
    int maxSize() default 3_000_000;
}
