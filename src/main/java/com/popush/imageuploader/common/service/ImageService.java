package com.popush.imageuploader.common.service;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Service;

import com.popush.imageuploader.common.db.ImageEntity;
import com.popush.imageuploader.common.db.ImageEntityMapper;
import com.popush.imageuploader.common.utility.ImageUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageEntityMapper imageEntityMapper;

    public String saveImage(ByteBuffer image) {
        var key = imageRepository.save(image);
        String thumbKey;
        try {
            thumbKey = imageRepository.save(ImageUtility.generateThumbnail(image, 300));
        } catch (IOException exception) {
            thumbKey = null;
        }

        imageEntityMapper.insert(ImageEntity.builder()
                                            .key(key)
                                            .thumbnailKey(thumbKey)
                                            .build());

        return imageRepository.getEndPointUri(key);
    }
}
