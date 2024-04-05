package de.daver.beyondplan.core;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestJsonParsing {

    public static void main(String[] args) {
        var json = """
                {
                    "name": "Max Mustermann",
                    "alter": 30,
                    "verheiratet": false
                }
                """;
        String s = """
                {
                  "name": "Max Mustermann",
                  "alter": 30,
                  "verheiratet": false,
                  "adressen": [
                    {
                      "strasse": "Hauptstraße 123",
                      "stadt": "Musterstadt",
                      "plz": "12345"
                    },
                    {
                      "strasse": "Nebenstraße 4",
                      "stadt": "Beispielstadt",
                      "plz": "67890"
                    }
                  ],
                  "kontakte": {
                    "email": "max@mustermann.de",
                    "telefon": "0123-4567890"
                  }
                }
                """;
        JsonObject jsonObject =JsonObject.parse(s);
        jsonObject.print();
        System.out.println(jsonObject.getString("name"));
    }

    static class JsonObject {

        public static JsonObject parse(String jsonString) {
            JsonObject jsonObject = new JsonObject();
            String[] parts = jsonString.replace("\"", "").split(",");
            AtomicInteger startIndex = new AtomicInteger(0);
            return parseObject(startIndex, parts);
        }

        private static JsonObject parseObject(AtomicInteger startIndex, String[] entries) {
            JsonObject jsonObject = new JsonObject();
            for(int i = startIndex.get(); i < entries.length; i++) {
                String entry = entries[i];
                String key = entry.split(":")[0].trim();
                if(entry.contains("{")) {
                    startIndex.set(i);
                    entries[i] = removeFirst('{', entries[i]);
                    jsonObject.add(key, parseObject(startIndex, entries));
                    i = startIndex.get();
                } else if(entry.contains("}")) {
                    entries[i] = removeFirst('}', entries[i]);
                    return jsonObject;
                } else if(entry.contains("[")) {
                    startIndex.set(i);
                    entries[i] = removeFirst('[', entries[i]);
                    jsonObject.add(key, parseArray(startIndex, entries));
                    i = startIndex.get();
                } else {
                    String value = entry.replace(key + ":", "").trim();
                    jsonObject.add(key, value);
                }
            }
            return jsonObject;
        }

        private static String removeFirst(char c, String s) {
            int i = s.indexOf(c);
            return s.substring(0, i) + s.substring(i + 1);
        }

        private static JsonArray parseArray(AtomicInteger startIndex, String[] entries) {
            JsonArray array = new JsonArray();
            for(int i = startIndex.get(); i < entries.length; i++) {
                String entry = entries[i];
                if(entry.contains("]")) {
                    entries[i] = removeFirst(']', entry);
                    startIndex.set(i);
                    return array;
                } else if(entry.contains("{")) {
                    startIndex.set(i);
                    entries[i] = removeFirst('{', entries[i]);
                    array.add(parseObject(startIndex, entries));
                    i = startIndex.get();
                } else if(entry.contains("[")) {
                    startIndex.set(i);
                    entries[i] = removeFirst('[', entries[i]);
                    array.add(parseArray(startIndex, entries));
                    i = startIndex.get();
                } else {
                    array.add(entry.trim());
                }
            }
            return array;
        }

        private Map<String, Object> map;

        private JsonObject() {
            map = new LinkedHashMap<>();
        }

        private void add(String key, Object value){
            map.put(key, value);
        }

        public String getString(String key){
            return (String) map.get(key);
        }

        public JsonObject getJsonObject(String key) {
            return (JsonObject) map.get(key);
        }

        public JsonArray getJsonArray(String key) {
            return (JsonArray) map.get(key);
        }

        private void print() {
            map.forEach((key, value) -> {
                if(value instanceof JsonObject object) {
                    System.out.println("Object:");
                    object.print();
                    System.out.println("---");
                }
                else if(value instanceof JsonArray array) {
                    System.out.println("Array:");
                    array.print();
                    System.out.println("---");
                }
                else System.out.println("K: " + key + " | V: " + value);
            });
        }
    }

    static class JsonArray {

        private List<Object> list;

        private JsonArray(){
            list = new ArrayList<>();
        }

        private void add(Object value){
            list.add(value);
        }

        private void print(){
            for(Object obj : list) {
                if(obj instanceof JsonObject object) object.print();
                else if(obj instanceof JsonArray array) array.print();
                else System.out.println(obj);
            }
        }
    }
}