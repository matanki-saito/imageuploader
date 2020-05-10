package com.popush.imageuploader.front.model;

import java.nio.ByteBuffer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Base64Image {
    private ByteBuffer buffer;
}
