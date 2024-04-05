package de.daver.beyondplan.core.json;

import java.util.ArrayList;
import java.util.List;

public class JsonArray {

    private List<Object> list;

    protected JsonArray() {
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
}
