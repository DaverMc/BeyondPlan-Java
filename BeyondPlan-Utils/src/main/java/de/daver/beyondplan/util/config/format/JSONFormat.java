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
                System.out.println("P: " + part);
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
        StringUtils.IndexedCharacter first = StringUtils.findFirstAppearence(control, JsonSyntax.ARRAY_START, JsonSyntax.OBJECT_START, JsonSyntax.OBJECT_END);
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
            System.out.println("KEY: " + key + " VALUE: " + node + " P_INDEX: " + partIndex.get() + " C_INDEX: " + controlIndex.get());

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
            System.out.println("VALUE: " + node + " P_INDEX: " + partIndex.get() + " C_INDEX: " + controlIndex.get());

            if (node.syntax == JsonSyntax.ARRAY_END) {
                System.out.println("ARR END:");
                return cache;
            }
        }
        return cache;
    }

    @Override
    public String serialize(Cache obj) {
        StringBuilder json = new StringBuilder("{");
        for(String key : obj.keys()) {
            json.append("\"").append(key).append("\":").append(ofObject(obj.get(key))).append(",");
        }
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }

    private String ofObject(ValueGetter objGetter) {
        Object obj = objGetter.value();
        if(obj == null) return "null";
        if(obj instanceof Cache cache) return serialize(cache);
        if(obj instanceof CacheList list) return serializeList(list);
        return "\"" + obj.toString() + "\""; //TODO Schauen dass Ints und boolean anders behandelt werden
    }

    @Override
    public String serializeList(CacheList cacheList) {
        StringBuilder json = new StringBuilder("[");
        for(int i = 0; i < cacheList.size(); i++) {
            json.append(ofObject(cacheList.get(i))).append(",");
        }
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("]");
        return json.toString();
    }

    @Override
    public CacheList parseList(String string) {
        return null;
    }

}
