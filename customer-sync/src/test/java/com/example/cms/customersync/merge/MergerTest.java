package com.example.cms.customersync.merge;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MergerTest {

    Merger merger = new Merger();

    @Test
    public void testSingleNewLeft() {
        Map<String, String> anchestor = empty();
        Map<String, String> left = map("a", "foo");
        Map<String, String> right = empty();

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "foo")));
    }

    @Test
    public void testSingleNewRight() {
        Map<String, String> anchestor = empty();
        Map<String, String> left = empty();
        Map<String, String> right = map("a", "foo");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "foo")));
    }

    @Test
    public void testSingleNewBothSame() {
        Map<String, String> anchestor = empty();
        Map<String, String> left = map("a", "foo");
        Map<String, String> right = map("a", "foo");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "foo")));
    }

    @Test(expected = ConflictException.class)
    public void testSingleNewBothConflict() {
        Map<String, String> anchestor = empty();
        Map<String, String> left = map("a", "foo");
        Map<String, String> right = map("a", "bar");

        merger.merge(anchestor, left, right);
    }

    @Test
    public void testSingleDeleteLeft() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = empty();
        Map<String, String> right = map("a", "foo");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(empty()));
    }

    @Test
    public void testSingleDeleteRight() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = map("a", "foo");
        Map<String, String> right = empty();

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(empty()));
    }

    @Test
    public void testSingleDeleteBoth() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = empty();
        Map<String, String> right = empty();

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(empty()));
    }

    @Test(expected = ConflictException.class)
    public void testSingleDeleteLeftChangeRight() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = empty();
        Map<String, String> right = map("a", "bar");

        merger.merge(anchestor, left, right);
    }

    @Test
    public void testSingleNoChange() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = map("a", "foo");
        Map<String, String> right = map("a", "foo");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "foo")));
    }

    @Test
    public void testSingleChangeLeft() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = map("a", "bar");
        Map<String, String> right = map("a", "foo");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "bar")));
    }

    @Test
    public void testSingleChangeRight() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = map("a", "foo");
        Map<String, String> right = map("a", "bar");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "bar")));
    }

    @Test
    public void testSingleChangeBothSame() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = map("a", "bar");
        Map<String, String> right = map("a", "bar");

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "bar")));
    }

    @Test(expected = ConflictException.class)
    public void testSingleChangeBothConflict() {
        Map<String, String> anchestor = map("a", "foo");
        Map<String, String> left = map("a", "bar");
        Map<String, String> right = map("a", "baz");

        merger.merge(anchestor, left, right);
    }

    @Test
    public void testMultiChange() {
        Map<String, String> anchestor = map("a", null, "b", "2", "c", "3");
        Map<String, String> left = map("a", "1", "b", "22", "c", "3");
        Map<String, String> right = map("a", "1", "b", "2", "c", null);

        Map<String, String> result = merger.merge(anchestor, left, right);
        assertThat(result, equalTo(map("a", "1", "b", "22")));
    }

    private Map<String, String> empty() {
        return new HashMap<>();
    }

    private Map<String, String> map(String k1, String v1) {
        HashMap<String, String> map = new HashMap<>();
        map.put(k1, v1);
        return map;
    }

    private Map<String, String> map(String k1, String v1, String k2, String v2) {
        Map<String, String> map = map(k1, v1);
        map.put(k2, v2);
        return map;
    }

    private Map<String, String> map(String k1, String v1, String k2, String v2, String k3, String v3) {
        Map<String, String> map = map(k1, v1, k2, v2);
        map.put(k3, v3);
        return map;
    }
}
