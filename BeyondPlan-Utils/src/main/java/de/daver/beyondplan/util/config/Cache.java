package de.daver.beyondplan.util.config;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

public class Cache {

    private final Map<String, Object> cache;

    public Cache() {
        this(LinkedHashMap::new);
    }

    public Cache(Supplier<Map<String, Object>> cacheSupplier) {
        this.cache = cacheSupplier.get();
    }

    public Set<String> keys() {
        return cache.keySet();
    }

    public ValueGetter get(String key) {
        Object value = cache.get(key);
        if(value instanceof CacheList list) return list.get(0);

        return new ValueGetter(cache.get(key));
    }

    public CacheList getList(String key) {
        Object value = cache.get(key);
        if(value == null) return null;
        if(value instanceof CacheList list) return list;
        CacheList list = new CacheList();
        list.add(value);
        return list;
    }

    public Cache getCache(String key) {
        Object value = cache.get(key);
        if(value == null) return null;
        if(value instanceof Cache cache) return cache;
        return null;
    }

    public void set(String key, Object value) {
        cache.put(key, value);
    }

    public String toString(ConfigFormat format) {
        return format.serialize(this);
    }

    public void save(OutputStream stream, ConfigFormat format) throws IOException {
        stream.write(toString(format).getBytes(StandardCharsets.UTF_8));
    }
}
