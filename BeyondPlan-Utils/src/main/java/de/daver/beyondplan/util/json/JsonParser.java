package de.daver.beyondplan.util.json;

import de.daver.beyondplan.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

public interface JsonParser {

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
        AtomicInteger startIndex = new AtomicInteger(0);
        return parseArray(startIndex, parts);
    }

    static Node keyValueRest(String entry){
        Node keyRest = keyRest(entry);
        String rest = keyRest.rest;

        int start = rest.indexOf('"');
        int end = rest.lastIndexOf('"');
        String value;
        if(start == -1 || end == -1){
            value = rest;
        } else value = rest.substring(start + 1, end);

        rest = rest.replace(value, "");


        return new Node(keyRest.key.trim(), value.trim(), rest);
    }

    static Node keyRest(String entry){
        String key = entry.split(":")[0];
        String rest = entry.replace(key + ":", "");
        int start = key.indexOf('"');
        int end = key.lastIndexOf('"');
        key = key.substring(start + 1, end);
        return new Node(key.trim(), "", rest);
    }

    record Node(String key, String value, String rest){}

    private static JsonObject parseObject(AtomicInteger startIndex, String[] entries) {
        JsonObject jsonObject = new JsonObject();
        for (int i = startIndex.get(); i < entries.length; i++) {

            String entry = entries[i];
            if (entry.contains("}")) {
                entry = StringUtils.removeFirst('}', entry);
                Node node = keyValueRest(entry);
                jsonObject.add(node.key, node.value);
                entries[i] = node.rest;
                startIndex.set(i);
                return jsonObject;
            }else if (entry.contains("[")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('[', entry);
                Node node = keyRest(entry);
                entries[i] = node.rest;
                jsonObject.add(node.key, parseArray(startIndex, entries));
                i = startIndex.get();
            } else if (entry.contains("{")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('{', entry);
                Node node = keyRest(entry);
                entries[i] = node.rest;
                jsonObject.add(node.key, parseObject(startIndex, entries));
                i = startIndex.get();
            } else {
                Node node = keyValueRest(entry);
                jsonObject.add(node.key, node.value);
            }
        }
        return jsonObject;
    }

    static String value(String entry) {
        int start = entry.indexOf('"');
        int end = entry.lastIndexOf('"');
        String value = entry.substring(start + 1, end);
        return value;
    }

    private static JsonArray parseArray(AtomicInteger startIndex, String[] entries) {
        JsonArray array = new JsonArray();
        for (int i = startIndex.get(); i < entries.length; i++) {
            String entry = entries[i];
            System.out.println("ENTRY: " + entry);
            if (entry.contains("]")) {
                entry = StringUtils.removeFirst(']', entry);
                String[] parts = entry.split("\"");
                String value = parts[1];
                if(!entry.isBlank()) array.add(value.replace("\"", ""));
                entries[i] = parts[2];
                startIndex.set(i);
                return array;
            } else if (entry.contains("[")) {
                startIndex.set(i);
                entries[i] = StringUtils.removeFirst('[', entries[i]);
                array.add(parseArray(startIndex, entries));
                i = startIndex.get();
            } else if (entry.contains("{")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('{', entry);
                entries[i] = entry;
                array.add(parseObject(startIndex, entries));
                i = startIndex.get();
            }  else if(!entry.isBlank()) {
                    array.add(value(entry));
                }
            }
        return array;
    }

}
