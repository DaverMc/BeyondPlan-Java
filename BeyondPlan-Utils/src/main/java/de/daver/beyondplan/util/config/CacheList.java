package de.daver.beyondplan.util.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CacheList {

    private final List<Object> cache;

    public CacheList() {
        this(ArrayList::new);
    }

    public CacheList(Supplier<List<Object>> supplier) {
        this.cache = supplier.get();
    }

    public ListGetter get() {
        return new ListGetter(cache);
    }

    public ValueGetter get(int index) {
        return new ValueGetter(cache.get(index));
    }

    public Cache getCache(int index) {
        if(cache.get(index) instanceof Cache c) return c;
        return null;
    }

    public CacheList getList(int index) {
        Object value = cache.get(index);
        if(value == null) return null;
        if(value instanceof CacheList list) return list;
        CacheList list = new CacheList();
        list.add(value);
        return list;
    }

    public void add(Object o) {
        cache.add(o);
    }

    public void addAll(Collection<Object> collection) {
        cache.addAll(collection);
    }

    public int size() {
        return cache.size();
    }

    public boolean contains(Object o) {
        return cache.contains(o);
    }
}
