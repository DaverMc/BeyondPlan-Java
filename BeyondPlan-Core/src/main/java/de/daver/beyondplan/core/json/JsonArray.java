package de.daver.beyondplan.core.json;

import de.daver.beyondplan.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonArray {

    private List<Object> list;

    private JsonArray() {
        list = new ArrayList<>();
    }

    protected void add(Object value) {
        list.add(value);
    }

    public void print() {
        for (Object obj : list) {
            if (obj instanceof JsonObject object) {
                System.out.println("OBJBEG");
                object.print();
                System.out.println("OBJEND");
            } else if (obj instanceof JsonArray array) {
                System.out.println("ARRBEG");
                array.print();
                System.out.println("ARREND");
            } else System.out.println(obj);
        }
    }

    public String getString(int index) {
        return (String) list.get(index);
    }

    public JsonObject getJsonObject(int index) {
        return (JsonObject) list.get(index);
    }

    public JsonArray getJsonArray(int index) {
        return (JsonArray) list.get(index);
    }

    public int size() {
        return list.size();
    }

    protected static JsonArray parseArray(AtomicInteger startIndex, String[] entries) {
        JsonArray array = new JsonArray();
        for (int i = startIndex.get(); i < entries.length; i++) {
            String entry = entries[i];
            if (entry.contains("{")) {
                startIndex.set(i);
                entry = StringUtils.removeFirst('{', entry);
                entries[i] = entry;
                array.add(JsonObject.parseObject(startIndex, entries));
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
