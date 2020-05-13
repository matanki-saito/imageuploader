package com.popush.imageuploader.common.service;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.popush.imageuploader.common.db.ImageEntity;
import com.popush.imageuploader.common.db.ImageEntityMapper;
import com.popush.imageuploader.common.db.ImageEntityReadonly;
import com.popush.imageuploader.common.model.ImageEntityCondition;
import com.popush.imageuploader.common.utility.ImageUtility;
import com.popush.imageuploader.common.utility.PageUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageEntityMapper imageEntityMapper;

    /**
     * Save Image to database and repository
     *
     * @param image image data
     *
     * @return endpoint URI
     */
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

    public <T> Page<T> getList(Pageable pageable, Function<ImageEntityReadonly, T> converter) {
        List<ImageEntityReadonly> list = imageEntityMapper.select(ImageEntityCondition.builder()
                                                                                      .build());

        return PageUtility.generatePageFromListWithConverter(list, pageable, converter);
    }

    public void deleteItem(long id) {
        var item = imageEntityMapper.select(ImageEntityCondition.builder().id(id).build());

        if (item.size() != 1) {
            throw new IllegalArgumentException();
        }

        imageEntityMapper.deleteById(id);
        imageRepository.delete(item.get(0).getThumbnailKey());
        imageRepository.delete(item.get(0).getKey());
    }
}
