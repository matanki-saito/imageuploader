package com.popush.imageuploader.common.service;

import java.nio.ByteBuffer;

import org.springframework.context.annotation.Profile;

@Profile("dev")
public class LocalFolderImageRepository implements ImageRepository {
    @Override
    public String save(ByteBuffer image) {
        return null;
    }

    @Override
    public ByteBuffer load(String key) {
        return null;
    }

    @Override
    public String getThumbnailEndPointUri(String key) {
        return null;
    }

    @Override
    public String getEndPointUri(String key) {
        return null;
    }
}
