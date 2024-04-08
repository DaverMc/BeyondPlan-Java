package de.daver.beyondplan.util.config.format;

import de.daver.beyondplan.util.config.Cache;
import de.daver.beyondplan.util.config.CacheList;
import de.daver.beyondplan.util.config.ConfigFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONFormatTest {

        @Test
        public void testSerializeEmptyCache() {
            Cache cache = new Cache();
            String expected = "{}";
            assertEquals(expected, ConfigFormat.JSON.serialize(cache));
        }

        @Test
        public void testSerializeSimpleValues() {
            Cache cache = new Cache();
            cache.set("name", "John Doe");
            cache.set("age", 30);
            String expected = "{\"name\":\"John Doe\",\"age\":\"30\"}";
            assertEquals(expected, ConfigFormat.JSON.serialize(cache));
        }

        @Test
        public void testSerializeNestedCache() {
            Cache cache = new Cache();
            Cache childCache = new Cache();
            childCache.set("city", "New York");
            cache.set("person", childCache);
            String expected = "{\"person\":{\"city\":\"New York\"}}";
            assertEquals(expected, ConfigFormat.JSON.serialize(cache));
        }

        @Test
        public void testSerializeList() {
            CacheList cacheList = new CacheList();
            cacheList.add("apple");
            cacheList.add("banana");
            String expected = "[\"apple\",\"banana\"]";
            assertEquals(expected, ConfigFormat.JSON.serializeList(cacheList));
        }
    }

