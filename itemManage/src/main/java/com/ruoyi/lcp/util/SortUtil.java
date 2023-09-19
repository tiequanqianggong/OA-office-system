package com.ruoyi.lcp.util;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.util.PropertyAccessor.PropertyAccessor;
public class SortUtil {
    public static <T, R extends Comparable<R>> List<T> sortByProperty(Collection<T> collection, PropertyAccessor<T, R> propertyAccessor) {
        return collection.stream()
                .sorted(Comparator.comparing(propertyAccessor::getProperty))
                .collect(Collectors.toList());
    }
    public static void extracted(List<Map.Entry<String, String>> entryList) throws JsonProcessingException {
        Collections.sort(entryList, Comparator.comparing(e -> Long.parseLong(e.getKey().split(":")[1])));
        // 构建一个新的 List，用于存储排序后的 Project 对象
        List<Project> sortedList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map.Entry<String, String> entry : entryList) {
            String projectJsonString = entry.getValue();
            Project projectObj = objectMapper.readValue(projectJsonString, Project.class);
            sortedList.add(projectObj);
        }
    }
}