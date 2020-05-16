package com.popush.imageuploader.front.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.popush.imageuploader.common.model.Base64Image;
import com.popush.imageuploader.common.model.Base64ImageCombinedSerializer.Base64ImageJsonDeserializer;
import com.popush.imageuploader.common.model.Base64ImageCombinedSerializer.Base64ImageJsonSerializer;
import com.popush.imageuploader.common.validation.ImageDataFilter;
import com.popush.imageuploader.common.validation.ReCaptchaTokenFilter;

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

    @ImageDataFilter(
            minHeight = 10,
            maxHeight = 1500,
            minWidth = 10,
            maxWidth = 1000,
            maxSize = 1_500_000,
            formatName = { "PNG", "JPEG", "GIF" }
    )
    @JsonSerialize(using = Base64ImageJsonSerializer.class)
    @JsonDeserialize(using = Base64ImageJsonDeserializer.class)
    private Base64Image image;

    @ReCaptchaTokenFilter
    private String reCaptchaToken;
}
