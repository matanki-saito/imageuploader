package com.popush.imageuploader.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntityCondition {
    private Long id;
    private String key;
    private Boolean enable;
}
