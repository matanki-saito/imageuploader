package com.popush.imageuploader.front.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.popush.imageuploader.front.model.Base64ImageCombinedSerializer.Base64ImageJsonDeserializer;
import com.popush.imageuploader.front.model.Base64ImageCombinedSerializer.Base64ImageJsonSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageForm {
    private String label;

    @JsonSerialize(using = Base64ImageJsonSerializer.class)
    @JsonDeserialize(using = Base64ImageJsonDeserializer.class)
    private Base64Image image;
}
