package de.daver.beyondplan.core.json;

import de.daver.beyondplan.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonObject {

    public static JsonObject parse(String jsonString) {
        String[] parts = jsonString.split(",");
        parts[0] = StringUtils.removeFirst('{', parts[0]);
        parts[parts.length - 1] = StringUtils.removeLast('}', parts[parts.length - 1]);
        AtomicInteger startIndex = new AtomicInteger(0);
        return parseObject(startIndex, parts);
    }

    protected static JsonObject parseObject(AtomicInteger startIndex, String[] entries) {
        JsonObject jsonObject = new JsonObject();
        for (int i = startIndex.get(); i < entries.length; i++) {
            String entry = entries[i];
            if (entry.contains("}")) {
                entry = StringUtils.removeFirst('}', entry);
                String[] parts = entry.split("\"");
                String key = entry.split(":")[0].trim();
                String value = parts[3];
                jsonObject.add(key, value);
                entries[i] = parts[4];
                startIndex.set(i);
                return jsonObject;
            }else if (entry.contains("[")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('[', entry);
                String key = entry.split(":")[0].trim();
                entries[i] = entry.replace(key + ":" , "");
                jsonObject.add(key, JsonArray.parseArray(startIndex, entries));
                i = startIndex.get();
            } else if (entry.contains("{")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('{', entry);
                String key = entry.split(":")[0].trim();
                entries[i] = entry.replace(key + ":", "");
                jsonObject.add(key, parseObject(startIndex, entries));
                i = startIndex.get();
            } else {
                String key = entry.split(":")[0].trim();
                String value = entry.replace(key + ":", "").trim();
                jsonObject.add(key, value);
            }
        }
        return jsonObject;
    }



    private final Map<String, Object> map;

    private JsonObject() {
        map = new LinkedHashMap<>();
    }

    private void add(String key, Object value) {
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
