package de.daver.beyondplan.util.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void simple() {
        Cache cache = new Cache();
        String name = "Hans";
        int age = 10;
        String gender = "male";
        String firstChild = "Marie";
        String secondChild = "Jane";
        String thirdChild = "John";
        CacheList children = new CacheList();
        children.add(firstChild);
        children.add(secondChild);
        children.add(thirdChild);

        cache.set("name", name);
        cache.set("age", age);
        cache.set("gender", gender);
        cache.set("children", children);

        assertEquals(name, cache.get("name").string());
        assertEquals(10, cache.get("age").bigDecimal().intValue());
        assertEquals(gender, cache.get("gender").string());
        assertEquals(firstChild, cache.getList("children").get(0).string());
        assertEquals(secondChild, cache.getList("children").get(1).string());
        assertEquals(thirdChild, cache.getList("children").get(2).string());
    }


}