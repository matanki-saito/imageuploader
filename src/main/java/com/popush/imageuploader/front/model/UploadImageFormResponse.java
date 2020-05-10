package com.popush.imageuploader.front.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadImageFormResponse {
    private String url;
}
