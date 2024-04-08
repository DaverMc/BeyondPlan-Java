package de.daver.beyondplan.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MultiMap<K, V> {

    private final Map<K, List<V>> map;
    private final Supplier<List<V>> listSupplier;

    public MultiMap() {
        this(HashMap::new);
    }

    public MultiMap(Supplier<Map<K, List<V>>> mapSupplier) {
        this(mapSupplier, ArrayList::new);
    }

    public MultiMap(Supplier<Map<K, List<V>>> mapSupplier, Supplier<List<V>> listSupplier) {
        this.map = mapSupplier.get();
        this.listSupplier = listSupplier;
    }

    public void add(K key, V value) {
        var list = map.computeIfAbsent(key, k -> listSupplier.get());
        list.add(value);
    }

    @SafeVarargs
    public final void addAll(K key, V... valueArray) {
        addAll(key, Arrays.asList(valueArray));
    }

    public void addAll(K key, Collection<V> collection) {
        var list = map.computeIfAbsent(key, k -> listSupplier.get());
        list.addAll(collection);
    }

    public List<V> getAll(K key) {
        return map.get(key);
    }

    public V getFirst(K key) {
        List<V> list = getAll(key);
        if(list == null || list.isEmpty()) return null;
        return list.getFirst();
    }

    public boolean remove(K key, V value) {
        List<V> list = getAll(key);
        return list.remove(value);
    }

    public void forEach(K key, Consumer<V> consumer) {
        getAll(key).forEach(consumer);
    }
}
