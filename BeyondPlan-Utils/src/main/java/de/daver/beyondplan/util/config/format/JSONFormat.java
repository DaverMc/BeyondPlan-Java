package de.daver.beyondplan.util.config.format;

import de.daver.beyondplan.util.StringUtils;
import de.daver.beyondplan.util.config.Cache;
import de.daver.beyondplan.util.config.CacheList;
import de.daver.beyondplan.util.config.ConfigFormat;
import de.daver.beyondplan.util.config.ValueGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JSONFormat implements ConfigFormat {

    public static void main(String[] args) {
        String json = """
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
        var cache = JSONFormat.JSON.parse(json);
        System.out.println("----SERIALIZED----");
        System.out.println();
        System.out.println(JSONFormat.JSON.serialize(cache));
    }

    @Override
    public Cache parse(String json) {
        List<String> controls = new ArrayList<>();
        List<String> parts = new ArrayList<>(); //Hier stehen alle Werte drin
        boolean open = false;
        int lastIndex = -1;

        for(int index : StringUtils.indexes(json, '"')){
            if(json.charAt(index - 1) == '\\') continue;

            if(open) {
                String part = json.substring(lastIndex + 1, index);
                parts.add(part);
            } else {
                String control = json.substring(lastIndex + 1, index).replaceAll("\\s+", "");
                controls.add(control);
            }
            open = !open;
            lastIndex = index;
        }

        AtomicInteger partIndex = new AtomicInteger(0);
        AtomicInteger controlIndex = new AtomicInteger(1);
        return parseCache(controlIndex, partIndex, parts, controls);
}

    @Override
    public CacheList parseList(String string) {
        return null;
    }

    private String control(String control) {
        if(control.isBlank() || control.equals(",")) return null;

        if(control.startsWith(":")) {
            return control.substring(1);
        }
        if(control.endsWith(",")) {
            return control.substring(0, control.length() - 1);
        }
        return control;
    }

    record SyntaxNode(char syntax, Object value) {}

    private SyntaxNode getValue(String control, AtomicInteger controlIndex, AtomicInteger partIndex, List<String> parts, List<String> controls) {
        StringUtils.IndexedCharacter first = StringUtils.findFirstAppearence(control, JsonSyntax.ARRAY_START, JsonSyntax.OBJECT_START, JsonSyntax.OBJECT_END, JsonSyntax.ARRAY_END);
        if(first == null) {
            String value;
            if (control.isBlank()) {
                value = parts.get(partIndex.getAndIncrement());
            } else value = control;

            return new SyntaxNode(' ', value);
        } else if(first.c() == JsonSyntax.ARRAY_START) {
            controls.set(controlIndex.get(), control.substring(first.index() + 1));
            CacheList list = parseList(controlIndex, partIndex, parts, controls);
            return new SyntaxNode(JsonSyntax.ARRAY_START, list);
        } else if(first.c() == JsonSyntax.OBJECT_START) {
            controls.set(controlIndex.get(), control.substring(first.index() + 1));
            return new SyntaxNode(JsonSyntax.OBJECT_START, parseCache(controlIndex, partIndex, parts, controls));
        } else if(first.c() == JsonSyntax.OBJECT_END || first.c() == JsonSyntax.ARRAY_END) {
            String value = control.substring(0, first.index());

            if (first.index() + 1 < controls.size()) {
                control = control.substring(first.index() + 1);
            } else {
                control = "";
            }
            controls.set(controlIndex.get(), control);
            return new SyntaxNode(first.c(), value);
        }
        return null;
    }

    private Cache parseCache(AtomicInteger controlIndex, AtomicInteger partIndex, List<String> parts, List<String> controls) {
        Cache cache = new Cache();
        System.out.println("OBJ BEG:");
        for(int i = controlIndex.get(); i < controls.size(); i++) {
            controlIndex.set(i);
            String control = control(controls.get(i));
            //System.out.println("CONTROL OBJ: " + control);
            if (control == null) continue;

            String key = parts.get(partIndex.getAndIncrement());
            SyntaxNode node = getValue(control, controlIndex, partIndex, parts, controls);
            cache.set(key, node.value);
            i = controlIndex.get();
            System.out.println("KEY: " + key + " VALUE: " + node.value + " P_INDEX: " + partIndex.get() + " C_INDEX: " + controlIndex.get());

            if(node.syntax == JsonSyntax.OBJECT_END) {
                System.out.println("OBJ END:");
                return cache;
            }
        }
        return cache;
    }

    private CacheList parseList(AtomicInteger controlIndex, AtomicInteger partIndex, List<String> parts, List<String> controls) {
        CacheList cache = new CacheList();
        System.out.println("ARR BEG:");
        for (int i = controlIndex.get(); i < controls.size(); i++) {
            controlIndex.set(i);
            String control = control(controls.get(i));
            //System.out.println("CONTROL ARR: " + control);
            if (control == null) continue;

            SyntaxNode node = getValue(control, controlIndex, partIndex, parts, controls);
            cache.add(node.value);
            i = controlIndex.get();
            System.out.println("VALUE: " + node.value + " P_INDEX: " + partIndex.get() + " C_INDEX: " + controlIndex.get());

            if (node.syntax == JsonSyntax.ARRAY_END) {
                System.out.println("ARR END:");
                return cache;
            }
        }
        return cache;
    }

    @Override
    public String serialize(Cache obj) {
        return serialize(obj, 1);
    }

    private String serialize(Cache obj, int depth) {
        StringBuilder json = new StringBuilder("{\n");
        boolean first = true;
        for (String key : obj.keys()) {
            if (!first) json.append(",\n");
            json.append(indent(depth)).append("\"").append(key).append("\": ");
            json.append(ofObject(obj.get(key), depth + 1));
            first = false;
        }
        json.append("\n").append(indent(depth - 1)).append("}");
        return json.toString();
    }


    @Override
    public String serializeList(CacheList cacheList) {
        return serializeList(cacheList, 1);
    }

    private String serializeList(CacheList cacheList, int depth) {
        StringBuilder json = new StringBuilder("[\n");
        boolean first = true;
        for (int i = 0; i < cacheList.size(); i++) {
            if (!first) json.append(",\n");
            json.append(indent(depth)).append(ofObject(cacheList.get(i), depth + 1));
            first = false;
        }
        json.append("\n").append(indent(depth - 1)).append("]");
        return json.toString();
    }

    private String ofObject(ValueGetter objGetter, int depth) {
        Object obj = objGetter.value();
        if (obj == null) return "null";
        if (obj instanceof Number || obj instanceof Boolean) return obj.toString();
        if (obj instanceof String) return "\"" + obj.toString().replace("\"", "\\\"") + "\"";
        if (obj instanceof Cache) return serialize((Cache) obj, depth);
        if (obj instanceof CacheList) return serializeList((CacheList) obj, depth);
        throw new IllegalArgumentException("Unsupported type: " + obj.getClass().getName());
    }

    private String indent(int depth) {
        return "  ".repeat(depth); // Nutzt zwei Leerzeichen pro Einrückungsebene
    }

}
