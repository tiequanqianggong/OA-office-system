package com.ruoyi.lcp.util.PropertyAccessor;

public interface PropertyAccessor<T, R extends Comparable<R>> {
    R getProperty(T obj);
}
