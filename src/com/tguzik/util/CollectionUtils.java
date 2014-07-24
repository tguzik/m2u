package com.tguzik.util;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class CollectionUtils {
    public static <T> List<T> safe( List<T> in ) {
        return in != null ? in : ImmutableList.<T>of();
    }
}
