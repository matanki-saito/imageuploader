package com.popush.imageuploader.common.utility;

import static java.awt.Image.SCALE_SMOOTH;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageUtility {

    /**
     * @see <a href="https://stackoverflow.com/questions/1069095/how-do-you-create-a-thumbnail-image-out-of-a-jpeg-in-java">link</a>
     */
    public ByteBuffer generateThumbnail(ByteBuffer image, int maxWidth) throws IOException {

        Image thumbnailImage;
        try (var in = new ByteArrayInputStream(image.array())) {
            thumbnailImage = ImageIO.read(in)
                                    .getScaledInstance(maxWidth, -1, SCALE_SMOOTH);
            thumbnailImage.flush();
        }

        BufferedImage bufferedImage = new BufferedImage(thumbnailImage.getWidth(null),
                                                        thumbnailImage.getHeight(null),
                                                        TYPE_INT_ARGB);

        bufferedImage.createGraphics().drawImage(thumbnailImage, 0, 0, null);

        ByteBuffer result;
        try (var out = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", out);
            result = ByteBuffer.wrap(out.toByteArray());
        }

        return result;
    }
}
