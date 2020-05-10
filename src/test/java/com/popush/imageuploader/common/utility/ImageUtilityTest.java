package com.popush.imageuploader.common.utility;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.util.ResourceUtils;

@ExtendWith(SoftAssertionsExtension.class)
class ImageUtilityTest {

    @Disabled
    @Test
    public void generateThumb() throws Exception {
        final byte[] data = Files.readAllBytes(ResourceUtils.getFile("classpath:500x500.png").toPath());
        final ByteBuffer buffer = ByteBuffer.wrap(data);

        final ByteBuffer result = ImageUtility.generateThumbnail(buffer, 300);

        Files.write(Path.of("./300x300.png"), result.array(), StandardOpenOption.CREATE_NEW);
    }
}
