package de.daver.beyondplan.util.json;

import de.daver.beyondplan.util.StringUtils;

import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

public interface JsonParser {

    static JsonObject ofHttpResponse(HttpResponse<String> response) {
        return ofString(response.body());
    }

    static JsonObject ofString(String jsonString) {
        String[] parts = jsonString.split(",");
        parts[0] = StringUtils.removeFirst('{', parts[0]);
        parts[parts.length - 1] = StringUtils.removeLast('}', parts[parts.length - 1]);
        AtomicInteger startIndex = new AtomicInteger(0);
        return parseObject(startIndex, parts);
    }

    static JsonArray arrayOfString(String jsonString) {
        String[] parts = jsonString.split(",");
        parts[0] = StringUtils.removeFirst('[', parts[0]);
        parts[parts.length - 1] = StringUtils.removeLast(']', parts[parts.length - 1]);
        AtomicInteger startIndex = new AtomicInteger(0);
        return parseArray(startIndex, parts);
    }

    private static JsonObject parseObject(AtomicInteger startIndex, String[] entries) {
        JsonObject jsonObject = new JsonObject();
        for (int i = startIndex.get(); i < entries.length; i++) {
            String entry = entries[i];
            if (entry.contains("}")) {
                entry = StringUtils.removeFirst('}', entry);
                String[] parts = entry.split("\"");
                String key = entry.split(":")[0].trim();
                String value = parts[3]; //FIXME Hier crasht empty array überprüfen wie viele teile es gibt
                jsonObject.add(key, value);
                entries[i] = parts[4];
                startIndex.set(i);
                return jsonObject;
            }else if (entry.contains("[")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('[', entry);
                String key = entry.split(":")[0].trim();
                entries[i] = entry.replace(key + ":" , "");
                jsonObject.add(key, parseArray(startIndex, entries));
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
                String value = entry.replace(key + ":", "").trim().replace("\"", "");
                jsonObject.add(key, value);
            }
        }
        return jsonObject;
    }

    private static JsonArray parseArray(AtomicInteger startIndex, String[] entries) {
        JsonArray array = new JsonArray();
        for (int i = startIndex.get(); i < entries.length; i++) {
            String entry = entries[i];
            if (entry.contains("{")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('{', entry);
                entries[i] = entry;
                array.add(parseObject(startIndex, entries));
                i = startIndex.get() - 1;
            }else if (entry.contains("]")) {
                entry = StringUtils.removeFirst(']', entry).trim();
                if(!entry.isBlank()) array.add(entry);
                entries[i] = entry;
                startIndex.set(i);
                return array;
            } else if (entry.contains("[")) {
                startIndex.set(i);
                entries[i] = StringUtils.removeFirst('[', entries[i]);
                array.add(parseArray(startIndex, entries));
                i = startIndex.get() - 1;
            } else {
                if(!entry.isBlank()) array.add(entry.trim());
            }
        }
        return array;
    }

}
