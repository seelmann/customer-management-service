package com.example.cms.customersync.merge;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Merger {

    public Map<String, String> merge(Map<String, String> anchestor, Map<String, String> left,
            Map<String, String> right) {
        Map<String, String> mergedProperties = new HashMap<>();
        Stream<String> keys = Stream.of(anchestor.keySet(), left.keySet(), right.keySet()).flatMap(Collection::stream)
                .distinct().sorted();
        keys.forEach(key -> {
            String mergedValue = merge(anchestor.get(key), left.get(key), right.get(key));
            if (mergedValue != null) {
                mergedProperties.put(key, mergedValue);
            }
        });
        return mergedProperties;
    }

    private String merge(String anchestor, String left, String right) {
        boolean leftAndRightDiffer = !Objects.equals(left, right);
        boolean leftChanged = !Objects.equals(anchestor, left);
        boolean rightChanged = !Objects.equals(anchestor, right);
        boolean bothChanged = leftChanged && rightChanged;

        if (bothChanged) {
            if (leftAndRightDiffer) {
                throw new ConflictException();
            } else {
                return left;
            }
        } else if (leftChanged) {
            return left;
        } else if (rightChanged) {
            return right;
        } else {
            return anchestor;
        }
    }

}
