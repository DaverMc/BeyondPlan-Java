package de.daver.beyondplan.util.config.format;

import de.daver.beyondplan.util.config.Cache;
import de.daver.beyondplan.util.config.CacheList;
import de.daver.beyondplan.util.config.ConfigFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONFormatTest {

    @Test
    public void testSerializeSimpleJsonObject() {
        Cache cache = new Cache();
        cache.set("firstName", "Jane");
        cache.set("lastName", "Doe");
        cache.set("age", 28);

        String expected = """
                {
                  "firstName": "Jane",
                  "lastName": "Doe",
                  "age": 28
                }""";
        String actual = ConfigFormat.JSON.serialize(cache);
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeJsonObjectWithList() {
        Cache cache = new Cache();
        CacheList hobbies = new CacheList();
        hobbies.add("Reading");
        hobbies.add("Cycling");
        cache.set("name", "John");
        cache.set("hobbies", hobbies);

        String expected = """
                {
                  "name": "John",
                  "hobbies": [
                    "Reading",
                    "Cycling"
                  ]
                }""";
        String actual = ConfigFormat.JSON.serialize(cache);
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeNestedJsonObject() {
        Cache person = new Cache();
        Cache address = new Cache();
        address.set("city", "New York");
        address.set("country", "USA");
        person.set("name", "Emily");
        person.set("address", address);

        String expected = """
                {
                  "name": "Emily",
                  "address": {
                    "city": "New York",
                    "country": "USA"
                  }
                }""";
        String actual = ConfigFormat.JSON.serialize(person);
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeJsonObjectWithVariousTypes() {
        Cache cache = new Cache();
        cache.set("name", "Bob");
        cache.set("age", 24);
        cache.set("verified", true);
        cache.set("balance", 1000.55);

        String expected = """
                {
                  "name": "Bob",
                  "age": 24,
                  "verified": true,
                  "balance": 1000.55
                }""";
        String actual = ConfigFormat.JSON.serialize(cache);
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeComplexJsonObject() {
        // Erstellen des Haupt-Cache-Objekts
        Cache cache = new Cache();

        // Füllen des Cache-Objekts mit Daten entsprechend dem Beispiel-JSON
        cache.set("name", "John Doe");
        cache.set("age", 30);
        cache.set("isAdmin", false);

        // Erstellen und Füllen des address-Objekts
        Cache address = new Cache();
        address.set("street", "123 Main St");
        address.set("city", "Anytown");
        address.set("postalCode", "12345");
        address.set("coordinates", null);
        cache.set("address", address);

        // Erstellen und Füllen der phoneNumbers-Liste
        CacheList phoneNumbers = new CacheList();
        phoneNumbers.add("+1234567890");
        phoneNumbers.add("+0987654321");
        cache.set("phoneNumbers", phoneNumbers);

        // Erstellen und Füllen der roles-Liste
        CacheList roles = new CacheList();
        roles.add("User");
        roles.add("Admin");
        cache.set("roles", roles);

        // Erstellen und Füllen des preferences-Objekts
        Cache preferences = new Cache();
        preferences.set("notifications", true);

        CacheList themes = new CacheList();
        themes.add("dark");
        themes.add("light");
        preferences.set("themes", themes);
        cache.set("preferences", preferences);

        cache.set("specialCharacters", "Here are some: \", \\, \b, \f, \n, \r, \t");
        cache.set("emptyObject", new Cache());
        cache.set("emptyArray", new CacheList());
        cache.set("nullValue", null);
        cache.set("", "Empty key name");
        cache.set("escapedCharacters", "\"Hello\", he said.");


        String expectedJson = """
                {
                  "name": "John Doe",
                  "age": 30,
                  "isAdmin": false,
                  "address": {
                    "street": "123 Main St",
                    "city": "Anytown",
                    "postalCode": "12345",
                    "coordinates": null
                  },
                  "phoneNumbers": [
                    "+1234567890",
                    "+0987654321"
                  ],
                  "roles": ["User", "Admin"],
                  "preferences": {
                    "notifications": true,
                    "themes": ["dark", "light"]
                  },
                  "specialCharacters": "Here are some: \\", \\\\, \\b, \\f, \\n, \\r, \\t",
                  "emptyObject": {},
                  "emptyArray": [],
                  "nullValue": null,
                  "": "Empty key name",
                  "escapedCharacters": "\\"Hello\\", he said."
                }
                """;
        String actualJson = ConfigFormat.JSON.serialize(cache);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testParseSimpleJsonObject() {
        String json = """
                {
                  "firstName": "Jane",
                  "lastName": "Doe",
                  "age": 28
                }""";
        Cache cache = ConfigFormat.JSON.parse(json);

        assertEquals("Jane", cache.get("firstName").string());
        assertEquals("Doe", cache.get("lastName").string());
        assertEquals(28, cache.get("age").bigDecimal().intValue());
    }

    @Test
    public void testParseJsonObjectWithList() {
        String json = """
                {
                  "name": "John",
                  "hobbies": [
                    "Reading",
                    "Cycling"
                  ]
                }""";
        Cache cache = ConfigFormat.JSON.parse(json);

        assertEquals("John", cache.get("name").string());
        CacheList hobbies = cache.get("hobbies").cacheList();
        assertNotNull(hobbies);
        assertTrue(hobbies.contains("Reading"));
        assertTrue(hobbies.contains("Cycling"));
    }

    @Test
    public void testParseNestedJsonObject() {
        String json = """
                {
                  "name": "Emily",
                  "address": {
                    "city": "New York",
                    "country": "USA"
                  }
                }""";
        Cache cache = ConfigFormat.JSON.parse(json);

        assertEquals("Emily", cache.get("name").string());
        Cache address = cache.get("address").cache();
        assertNotNull(address);
        assertEquals("New York", address.get("city").string());
        assertEquals("USA", address.get("country").string());
    }

    @Test
    public void testParseJsonObjectWithVariousTypes() {
        String json = """
                {
                  "name": "Bob",
                  "age": 24,
                  "verified": true,
                  "balance": 1000.55
                }""";
        Cache cache = ConfigFormat.JSON.parse(json);

        assertEquals("Bob", cache.get("name").string());
        assertEquals(24, cache.get("age").bigDecimal().intValue());
        assertEquals(true, cache.get("verified").bool());
        assertEquals(1000.55, cache.get("balance").bigDecimal().doubleValue(), 0.001);
    }
}
