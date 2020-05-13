package com.popush.imageuploader.common.model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @see <a href="https://stackoverflow.com/questions/1069095/how-do-you-create-a-thumbnail-image-out-of-a-jpeg-in-java">link</a>
 * @see <a href="https://medium.com/@bau1537/jackson%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%9Fjava%E3%81%A8json%E3%81%AE%E7%9B%B8%E4%BA%92%E5%A4%89%E6%8F%9B-a88dad6d8d54">link</a>
 */
@JsonComponent
public class Base64ImageCombinedSerializer {
    public static class Base64ImageJsonSerializer extends JsonSerializer<Base64Image> {
        @Override
        public void serialize(Base64Image base64Image,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {

            final String base64 = Base64.getEncoder().encodeToString(base64Image.getBuffer().array());
            jsonGenerator.writeString(base64);
        }
    }

    public static class Base64ImageJsonDeserializer extends JsonDeserializer<Base64Image> {
        @Override
        public Base64Image deserialize(JsonParser jsonParser,
                                       DeserializationContext deserializationContext)
                throws IOException {

            final String base64 = jsonParser.readValueAs(String.class);
            final var data = Base64.getDecoder().decode(base64);

            return Base64Image.builder()
                              .buffer(ByteBuffer.wrap(data))
                              .build();
        }
    }

}
