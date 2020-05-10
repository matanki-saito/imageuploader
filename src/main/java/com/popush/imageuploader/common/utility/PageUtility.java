package com.popush.imageuploader.common.utility;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtility {
    public <T, Q> Page<Q> generatePageFromListWithConverter(List<T> list, Pageable pageable,
                                                            Function<T, Q> converter) {

        if (list.size() < pageable.getOffset()) {
            return Page.empty();
        }

        var subList = list.subList((int) pageable.getOffset(),
                                   (int) Math.min(pageable.getOffset() + pageable.getPageSize(),
                                                  list.size()));

        return new PageImpl<>(
                subList.stream().map(converter).collect(Collectors.toList()),
                pageable,
                list.size());
    }

    public <T> Page<T> generatePageFromList(List<T> list, Pageable pageable) {
        return generatePageFromListWithConverter(list, pageable, Function.identity());
    }
}
