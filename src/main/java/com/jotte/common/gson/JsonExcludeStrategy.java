package com.jotte.common.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class JsonExcludeStrategy {
    public static ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(JsonExclude.class) != null;
        }
    };
}
