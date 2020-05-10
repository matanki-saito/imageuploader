package com.popush.imageuploader.common.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {
        "com.popush.imageuploader.common.db"
})
public class MapperTestApplication {
    /* 何も書かないこと */
    /* http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/ */
}
