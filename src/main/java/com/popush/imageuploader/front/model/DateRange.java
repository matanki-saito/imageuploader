package com.popush.imageuploader.front.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRange {
    private LocalDateTime begin;
    private LocalDateTime end;
}
