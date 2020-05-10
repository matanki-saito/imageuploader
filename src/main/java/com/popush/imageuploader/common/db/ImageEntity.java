package com.popush.imageuploader.common.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    private String key;
    private String thumbnailKey;
    private boolean enable;
}
