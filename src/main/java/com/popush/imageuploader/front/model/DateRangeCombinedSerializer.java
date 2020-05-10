package com.popush.imageuploader.front.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class DateRangeCombinedSerializer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static class DateRangeJsonSerializer extends JsonSerializer<DateRange> {

        @Override
        public void serialize(
                DateRange dateRange,
                JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {

            jsonGenerator.writeString(makeSerializedDateRange(dateRange));
        }

        private static String makeSerializedDateRange(
                DateRange dateRange) {
            return String.format("%s - %s",
                                 dateRange.getBegin().format(FORMATTER),
                                 dateRange.getEnd().format(FORMATTER));
        }
    }

    public static class DateRangeJsonDeserializer
            extends JsonDeserializer<DateRange> {

        @Override
        public DateRange deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext)
                throws IOException {

            final String[] beginAndEnd = jsonParser.readValueAs(String.class).split(" - ");

            final LocalDateTime begin = LocalDateTime.parse(beginAndEnd[0], FORMATTER);
            final LocalDateTime end = LocalDateTime.parse(beginAndEnd[1], FORMATTER);

            return DateRange.builder().begin(begin).end(end).build();
        }
    }
}
