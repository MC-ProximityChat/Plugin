package com.proximitychat.plugin.util.tuples;

import java.util.Map;

public class ImmutablePair<K, V> implements Map.Entry<K, V> {

    protected final K key;

    protected final V value;

    protected ImmutablePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> ImmutablePair<K, V> of(K key, V value) {
        return new ImmutablePair<>(key, value);
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("Cannot modify immutable pair!");
    }
}
