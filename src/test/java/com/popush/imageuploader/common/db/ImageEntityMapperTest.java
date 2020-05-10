package com.popush.imageuploader.common.db;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.popush.imageuploader.common.model.ImageEntityCondition;

@ExtendWith(SpringExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ImageEntityMapperTest {

    /* こうしないとIntellijでアラートが出る */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ImageEntityMapper mapper;

    @Test
    public void crud(SoftAssertions softly) {
        var key = "gesogeso";

        // create
        var cnt = mapper.insert(ImageEntity.builder()
                                           .key(key)
                                           .enable(true)
                                           .thumbnailKey("gesogeso-thumb")
                                           .build());
        softly.assertThat(cnt).isEqualTo(1);

        // read
        var result = mapper.select(ImageEntityCondition.builder()
                                                       .key(key)
                                                       .build());
        softly.assertThat(result).hasSize(1);
        softly.assertThat(result.get(0))
              .isEqualToIgnoringGivenFields(ImageEntityReadonly.builder()
                                                               .thumbnailKey("gesogeso-thumb")
                                                               .enable(true)
                                                               .key(key)
                                                               .build(), "imageId", "createdAt", "updatedAt");

        // update
        mapper.update(ImageEntityCondition.builder()
                                          .key(key)
                                          .build(),
                      ImageEntity.builder()
                                 .enable(false)
                                 .thumbnailKey("hogehoge-thumb")
                                 .build());
        result = mapper.select(ImageEntityCondition.builder()
                                                   .key(key)
                                                   .build());
        softly.assertThat(result).hasSize(1);
        softly.assertThat(result.get(0))
              .isEqualToIgnoringGivenFields(ImageEntityReadonly.builder()
                                                               .thumbnailKey("hogehoge-thumb")
                                                               .enable(false)
                                                               .key(key)
                                                               .build(), "imageId", "createdAt", "updatedAt");

        // delete
        mapper.deleteByKey(key);
        result = mapper.select(ImageEntityCondition.builder()
                                                   .key(key)
                                                   .build());
        softly.assertThat(result).hasSize(0);

    }
}
