package de.daver.beyondplan.util;

import java.util.*;
import java.util.function.Consumer;

public class MultiHashMap<K, V> {

    private final Map<K, List<V>> map;

    public MultiHashMap() {
        this.map = new HashMap<>();
    }

    public void add(K key, V value) {
        var list = map.computeIfAbsent(key, k -> new ArrayList<>());
        list.add(value);
    }

    @SafeVarargs
    public final void addAll(K key, V... valueArray) {
        var list = map.computeIfAbsent(key, k -> new ArrayList<>());
        list.addAll(Arrays.asList(valueArray));
    }

    public void addAll(K key, Collection<V> collection) {
        var list = map.computeIfAbsent(key, k -> new ArrayList<>());
        list.addAll(collection);
    }

    public List<V> getAll(K key) {
        return map.get(key);
    }

    public void forEach(K key, Consumer<V> consumer) {
        getAll(key).forEach(consumer);
    }
}
