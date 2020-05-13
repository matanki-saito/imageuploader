package com.popush.imageuploader.common.validation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import com.popush.imageuploader.common.model.Base64Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageContentFilterValidator implements ConstraintValidator<ImageContentFilter, Base64Image> {
    @Override
    public boolean isValid(Base64Image value, ConstraintValidatorContext context) {

        BufferedImage img;
        try (var in = new ByteArrayInputStream(value.getBuffer().array())) {
            img = ImageIO.read(in);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("./traindata");
        tesseract.setLanguage("jpn");

        String str;
        try {
            str = tesseract.doOCR(img);
        } catch (TesseractException e) {
            throw new IllegalArgumentException(e);
        }

        // 結果
        log.info(str);

        return true;
    }
}
