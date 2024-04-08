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


        cache.set("name", name);
        cache.set("age", age);
        cache.set("gender", gender);
        cache.set("children", firstChild, secondChild, thirdChild);

        assertEquals(name, cache.get("name").cast(String.class));
        assertEquals(10, cache.get("age").cast(Integer.class));
        assertEquals(gender, cache.get("gender").cast(String.class));
        assertEquals(firstChild, cache.getList("children").get(0).cast(String.class));
        assertEquals(secondChild, cache.getList("children").get(1).cast(String.class));
        assertEquals(thirdChild, cache.getList("children").get(2).cast(String.class));
    }


}