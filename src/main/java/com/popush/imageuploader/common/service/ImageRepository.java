package com.popush.imageuploader.common.service;

import java.nio.ByteBuffer;

public interface ImageRepository {
    String save(ByteBuffer image);

    ByteBuffer load(String key);

    String getThumbnailEndPointUri(String key);

    String getEndPointUri(String key);

    void delete(String key);
}
