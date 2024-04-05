package de.daver.beyondplan.core.json;


import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject {

    private final Map<String, Object> map;

    protected JsonObject() {
        map = new LinkedHashMap<>();
    }

    protected void add(String key, Object value) {
        map.put(key, value);
    }

    public String getString(String key) {
        return (String) map.get("\"" + key + "\"");
    }

    public JsonObject getJsonObject(String key) {
        return (JsonObject) map.get("\"" + key + "\"");
    }

    public JsonArray getJsonArray(String key) {
        return (JsonArray) map.get("\"" + key + "\"");
    }

    public void print() {
        map.forEach((key, value) -> {
            if (value instanceof JsonObject object) {
                System.out.println("OBJBEG: " + key);
                object.print();
                System.out.println("OBJEND");
            } else if (value instanceof JsonArray array) {
                System.out.println("ARRBEG: " + key);
                array.print();
                System.out.println("ARREND");
            } else System.out.println("K:" + key + "|V: " + value);
        });
    }
}
