package de.daver.beyondplan.util.json;


import de.daver.beyondplan.util.StringTransformer;

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

    public <T> T get(String key, StringTransformer<T> transformer) {
        return transformer.transform(getString(key));
    }

    @Override
    public String toString() {
        return toStringImpl(0);
    }

    protected String toStringImpl(int indent) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        boolean first = true; // Flag, um das erste Element ohne führendes Komma zu behandeln
        String indentString = " ".repeat(indent); // Erzeugt die Einrückung

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                jsonBuilder.append(",\n");
            }
            jsonBuilder.append(indentString).append("  \"").append(entry.getKey()).append("\": ");
            Object value = entry.getValue();
            if (value instanceof JsonObject) {
                jsonBuilder.append(((JsonObject) value).toStringImpl(indent + 2));
            } else if (value instanceof JsonArray) {
                jsonBuilder.append(((JsonArray) value).toStringImpl(indent + 2));
            } else if (value instanceof String) {
                jsonBuilder.append("\"").append(value).append("\"");
            } else {
                jsonBuilder.append(value);
            }
            first = false;
        }
        jsonBuilder.append("\n").append(indentString).append("}");
        return jsonBuilder.toString();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
