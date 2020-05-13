package com.popush.imageuploader.front.model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popush.imageuploader.ImageuploaderTestApplication;
import com.popush.imageuploader.common.model.Base64Image;

@SpringBootTest(classes = ImageuploaderTestApplication.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class Base64ImageCombinedSerializerTest {
    @Autowired
    private ObjectMapper objectMapper;

    private final static String JSON = "\"iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAAAAACoBHk5AAAABG"
                                       + "dBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6A"
                                       + "AAdTAAAOpgAAA6mAAAF3CculE8AAAAAmJLR0QA/4ePzL8AAA"
                                       + "AHdElNRQfkBQkNAhtEYgvzAAAAFUlEQVQI12M4AwIMR8/sBZ"
                                       + "IQNhIJACklE9c3fL62AAAAJXRFWHRkYXRlOmNyZWF0ZQAyMD"
                                       + "IwLTA1LTA5VDIyOjAyOjI3KzA5OjAwkCAyNAAAACV0RVh0ZG"
                                       + "F0ZTptb2RpZnkAMjAyMC0wNS0wOVQyMjowMjoyNyswOTowMO"
                                       + "F9iogAAAAASUVORK5CYII=\"";

    @Test
    public void testDeserialize(SoftAssertions softly) throws IOException {

        final var hDateRange = objectMapper.readValue(JSON, Base64Image.class);

        final byte[] data = Files.readAllBytes(ResourceUtils.getFile("classpath:5x5.png").toPath());

        softly.assertThat(hDateRange)
              .isEqualTo(Base64Image.builder()
                                    .buffer(ByteBuffer.wrap(data))
                                    .build());
    }

    @Test
    public void testSerialize(SoftAssertions softly) throws IOException {
        final byte[] data = Files.readAllBytes(ResourceUtils.getFile("classpath:5x5.png").toPath());

        final Base64Image object = Base64Image.builder()
                                              .buffer(ByteBuffer.wrap(data))
                                              .build();

        var json = objectMapper.writeValueAsString(object);

        softly.assertThat(json)
              .isEqualTo(JSON);
    }
}
