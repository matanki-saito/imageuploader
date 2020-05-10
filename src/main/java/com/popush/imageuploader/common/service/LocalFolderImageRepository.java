package com.popush.imageuploader.common.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("dev")
@Repository
public class LocalFolderImageRepository implements ImageRepository {

    public static final String BASE_DIR = "./data/image";

    @Override
    public String save(ByteBuffer image) {
        var uuid = UUID.randomUUID().toString();
        Path saveFilePath = Paths.get(BASE_DIR, uuid + ".png");

        try {
            Files.write(saveFilePath, image.array());
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }

        return uuid;
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
        return "/front/localimage/" + key;
    }
}
