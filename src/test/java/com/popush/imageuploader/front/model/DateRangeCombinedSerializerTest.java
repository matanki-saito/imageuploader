package com.popush.imageuploader.front.model;

import java.io.IOException;
import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popush.imageuploader.ImageuploaderTestApplication;

@SpringBootTest(classes = ImageuploaderTestApplication.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class DateRangeCombinedSerializerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDeserialize(SoftAssertions softly) throws IOException {
        String json = "\"2020-10-20 12:00:00 - 2020-10-31 15:30:50\"";
        var hDateRange = objectMapper.readValue(json, DateRange.class);

        softly.assertThat(hDateRange)
              .isEqualTo(DateRange.builder()
                                  .begin(LocalDateTime.of(2020, 10, 20, 12, 0, 0))
                                  .end(LocalDateTime.of(2020, 10, 31, 15, 30, 50))
                                  .build());
    }

    @Test
    public void testSerialize(SoftAssertions softly) throws IOException {
        final DateRange object = DateRange.builder()
                                          .begin(LocalDateTime.of(2020, 10, 20, 12, 0, 0))
                                          .end(LocalDateTime.of(2020, 10, 31, 15, 30, 50))
                                          .build();
        var json = objectMapper.writeValueAsString(object);

        softly.assertThat(json)
              .isEqualTo("\"2020-10-20 12:00:00 - 2020-10-31 15:30:50\"");
    }
}
