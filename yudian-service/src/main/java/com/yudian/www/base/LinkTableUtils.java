package com.yudian.www.base;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Component
public class LinkTableUtils {



    private <T, R> Map<Long, R> linkTable(
            TableEnum tableEnum,
            Collection<T> records, Function<T, Long> getKey,
            Function<R, Long> getRKey
    ) {
        if (records.isEmpty()) {
            return new HashMap<>();
        }


        Set<Long> idSet = records.stream().map(getKey).filter(Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return new HashMap<>();
        }

        IService<R> iService = (IService) ApplicationContextUtil.getBean(tableEnum.tClass);
        return iService.listByIds(idSet)
                .stream()
                .collect(Collectors.toMap(getRKey, data -> data));
    }


    private <T, T2, R> R tableLink(
            TableEnum tableEnum,
            SFunction<T2, Long> targetSearchField,
            List<T> records, Function<T, Long> sourceField,
            Collector<T2, ?, R> collector
    ) {
        if (records.isEmpty()) {
            return (R) collector.supplier().get();
        }


        Set<Long> idSet = records.stream().map(sourceField).filter(Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return (R) collector.supplier().get();
        }

        IService<T2> iService = (IService) ApplicationContextUtil.getBean(tableEnum.tClass);
        return iService.list(Wrappers.<T2>lambdaQuery()
                .in(targetSearchField, idSet))
                .stream()
                .collect(collector);
    }

    public enum TableEnum {

        ;

        private final String tableRemark;
        private final Class tClass;

        TableEnum(String tableRemark, Class tClass) {
            this.tableRemark = tableRemark;
            this.tClass = tClass;
        }
    }
}
