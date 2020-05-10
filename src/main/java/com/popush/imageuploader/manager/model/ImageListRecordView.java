package com.popush.imageuploader.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageListRecordView {
    private long id;
    private boolean active;
    private String thumbnailUrl;
    private long timestamp;
}
