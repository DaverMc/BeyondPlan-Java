package de.daver.beyondplan.util.json;

import de.daver.beyondplan.util.StringTransformer;

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

    public String getString(int index) {
        return (String) list.get(index);
    }

    public <T> T get(int index, StringTransformer<T> transformer) {
        return transformer.transform(getString(index));
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

    @Override
    public String toString() {
        return toStringImpl(0);
    }

    protected String toStringImpl(int indent) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        boolean first = true; // Flag, um das erste Element ohne führendes Komma zu behandeln
        String indentString = " ".repeat(indent);

        for (Object element : list) {
            if (!first) {
                jsonBuilder.append(",\n");
            }
            jsonBuilder.append(indentString).append("  ");
            if (element instanceof JsonObject) {
                jsonBuilder.append(((JsonObject) element).toStringImpl(indent + 2));
            } else if (element instanceof JsonArray) {
                jsonBuilder.append(((JsonArray) element).toStringImpl(indent + 2));
            } else if (element instanceof String) {
                jsonBuilder.append("\"").append(element).append("\"");
            } else {
                jsonBuilder.append(element);
            }
            first = false;
        }
        jsonBuilder.append("\n").append(indentString).append("]");
        return jsonBuilder.toString();
    }
}
