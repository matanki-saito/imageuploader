package com.popush.imageuploader.common.validation;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.popush.imageuploader.common.model.Base64Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageDataFilterValidator implements ConstraintValidator<ImageDataFilter, Base64Image> {
    private Set<String> formatNameSet;

    private int minWidth;

    private int minHeight;

    private int maxWidth;

    private int maxHeight;

    private int maxSize;

    private static Dimension getImageSize(InputStream in) throws IOException {
        final BufferedImage image = ImageIO.read(in);
        if (image == null) {
            throw new IOException("Could not read as image");
        }
        return new Dimension(image.getWidth(), image.getHeight());
    }

    private static String getImageFormatName(InputStream in) throws IOException {
        try (ImageInputStream imageIn = ImageIO.createImageInputStream(in)) {
            final Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageIn);

            if (!imageReaders.hasNext()) {
                throw new IOException("The image has no format");
            }

            final ImageReader imageReader = imageReaders.next();

            if (imageReaders.hasNext()) {
                log.info("The image has multiple formats");
            }

            return imageReader.getFormatName();
        }
    }

    @Override
    public void initialize(ImageDataFilter constraintAnnotation) {
        formatNameSet = constraintAnnotation.formatName().length > 0
                        ? new HashSet<>(Arrays.asList(constraintAnnotation.formatName()))
                        : Collections.emptySet();
        minWidth = constraintAnnotation.minWidth();
        minHeight = constraintAnnotation.minHeight();
        maxWidth = constraintAnnotation.maxWidth();
        maxHeight = constraintAnnotation.maxHeight();
        maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(Base64Image value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null || value.getBuffer() == null || value.getBuffer().array().length == 0) {
            return true;
        }

        if (value.getBuffer().array().length > maxSize) {
            context.buildConstraintViolationWithTemplate(String.format("file size over %d", maxSize))
                   .addConstraintViolation();
            return false;
        }

        try (InputStream in = new ByteArrayInputStream(value.getBuffer().array())) {
            in.mark(0);

            // Check format name
            in.reset();
            final String formatName = getImageFormatName(in).toUpperCase(Locale.US);
            if (!formatNameSet.contains(formatName)) {
                context.buildConstraintViolationWithTemplate(String.format("not allow format %s", formatName))
                       .addConstraintViolation();
                return false;
            }

            // Check rectangle range
            in.reset();
            final Dimension rect = getImageSize(in);
            var result = rect.getWidth() >= minWidth
                         && rect.getWidth() <= maxWidth
                         && rect.getHeight() >= minHeight
                         && rect.getHeight() <= maxHeight;

            if (!result) {
                context.buildConstraintViolationWithTemplate(
                        String.format("Allow size width %d-%d, height %d-%d, Current: w%.0f-h%.0f",
                                      minWidth, maxWidth, minHeight, maxHeight,
                                      rect.getWidth(),
                                      rect.getHeight()))
                       .addConstraintViolation();
            }

            return result;

        } catch (IOException e) {
            log.warn(e.getMessage());
            return false;
        }
    }
}
