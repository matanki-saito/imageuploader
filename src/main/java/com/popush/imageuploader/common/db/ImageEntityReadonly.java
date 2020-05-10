package com.popush.imageuploader.common.db;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntityReadonly extends ImageEntity {
    private long imageId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
