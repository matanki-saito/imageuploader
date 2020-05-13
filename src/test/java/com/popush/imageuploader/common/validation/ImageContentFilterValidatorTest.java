package com.popush.imageuploader.common.validation;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.util.ResourceUtils;

import com.popush.imageuploader.common.model.Base64Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@ExtendWith(SoftAssertionsExtension.class)
class ImageContentFilterValidatorTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void isValid(SoftAssertions softly) throws Exception {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        final byte[] data = Files.readAllBytes(ResourceUtils.getFile("classpath:spams/spam3.png").toPath());
        final Base64Image target = Base64Image.builder()
                                              .buffer(ByteBuffer.wrap(data))
                                              .build();

        final Set<ConstraintViolation<Base64ImageDummy1>> violations = validator
                .validate(
                        Base64ImageDummy1.builder()
                                         .file(target)
                                         .build()
                );

        softly.assertThat(violations).isEmpty();
    }

    @AllArgsConstructor
    @Data
    @Builder
    private static class Base64ImageDummy1 {
        @ImageContentFilter
        Base64Image file;
    }
}
