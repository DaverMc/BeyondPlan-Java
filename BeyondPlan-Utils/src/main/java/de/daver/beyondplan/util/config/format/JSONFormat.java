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
        JSONFormat.JSON.parse(json);
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
                //System.out.println("PART: " + part);
            } else {
                String control = json.substring(lastIndex + 1, index).replaceAll("\\s+", "");
                //System.out.println("LAST: " + lastIndex + " INDEX: " + index);
                controls.add(control);
                //System.out.println("CONTROL: " + control);
            }
            open = !open;
            lastIndex = index;
        }

        AtomicInteger partIndex = new AtomicInteger(0);
        return parseCache(partIndex, parts, controls);
}

    private String control(String control) {
        System.out.println(control);
        if(control.isBlank() || control.equals(",")) return null;

        if(control.startsWith(":")) {
            return control.substring(1);
        }
        if(control.endsWith(",")) {
            return control.substring(0, control.length() - 1);
        }
        return control;
    }

    private Object getValue(String control, AtomicInteger controlIndex, AtomicInteger partIndex, List<String> parts, List<String> controls) {
        StringUtils.IndexedCharacter first = StringUtils.findFirstAppearence(control, JsonSyntax.ARRAY_START, JsonSyntax.OBJECT_START, JsonSyntax.OBJECT_END);
        if(first== null) {
            Object value;
            if (control.isBlank()) {
                value = parts.get(partIndex.getAndIncrement());
            } else value = control;


            return value;
        } else if(first.c() == JsonSyntax.ARRAY_START) {
            controls.set(controlIndex.get(), control.substring(first.index() + 1));
            return parseList(partIndex, parts, controls);
        } else if(first.c() == JsonSyntax.OBJECT_START) {
            controls.set(controlIndex.get(), control.substring(first.index() + 1));
            return parseCache(partIndex, parts, controls);
        } else if(first.c() == JsonSyntax.OBJECT_END) {
            String value = control.substring(0, first.index());

            if (first.index() + 1 < controls.size()) {
                control = control.substring(first.index() + 1);
            } else {
                control = "";

            }
            controls.set(controlIndex.get(), control);
            return value;
        }

    }

    private Cache parseCache(AtomicInteger partIndex, List<String> parts, List<String> controls) {
        Cache cache = new Cache();
        for(int i = partIndex.get(); i < controls.size(); i++){
            String control = control(controls.get(i));
            if(control == null) continue;
            //System.out.println("R2: " + control);
            String key = parts.get(partIndex.getAndIncrement());
            StringUtils.IndexedCharacter first = StringUtils.findFirstAppearence(control, JsonSyntax.ARRAY_START, JsonSyntax.OBJECT_START, JsonSyntax.OBJECT_END);
            if(first== null) {
                String value;
                if(control.isBlank()) {
                    value = parts.get(partIndex.getAndIncrement());
                }
                else value = control;

                System.out.println("K: " + key + " V: " + value + " I: " +  partIndex.get());
                cache.set(key, value);
            } else if(first.c() == JsonSyntax.ARRAY_START) {
                controls.set(i, control.substring(first.index() + 1));
                System.out.println("KEY: " + key + " : ARRAY" + " I: " +  partIndex.get());
                cache.set(key, parseList(partIndex, parts, controls));
            } else if(first.c() == JsonSyntax.OBJECT_START) {
                controls.set(i, control.substring(first.index() + 1));
                System.out.println("KEY: " + key + " : OBJECT" + " I: " +  partIndex.get());
                cache.set(key, parseCache(partIndex, parts, controls));
                System.out.println("AI: " + partIndex.getAndIncrement());
            } else if(first.c() == JsonSyntax.OBJECT_END) {
                String value = control.substring(0, first.index());
                cache.set(key, value);

                if(first.index() + 1 < controls.size())  {
                    control = control.substring(first.index() + 1);
                } else {
                    control = "";

                }
                controls.set(i, control);
                System.out.println("K: " + key + " V: " + value + " OBJ END I: " +  partIndex.get());
                return cache;
            }
        }
        return cache;
    }

    private CacheList parseList(AtomicInteger partIndex, List<String> parts, List<String> controls) {
        CacheList cache = new CacheList();
        for(int i = partIndex.get(); i < controls.size(); i++){
            String control = control(controls.get(i));
            if(control == null) continue;

            StringUtils.IndexedCharacter first = StringUtils.findFirstAppearence(control, JsonSyntax.ARRAY_START, JsonSyntax.OBJECT_START, JsonSyntax.ARRAY_END);
            if(first== null) {
                String value;
                if(control.isBlank()) value = parts.get(partIndex.getAndIncrement());
                else value = control;

                System.out.println( " V: " + value + " I: " +  partIndex.get());
                cache.add(value);
            } else if(first.c() == JsonSyntax.ARRAY_START) {
                controls.set(i, control.substring(first.index() + 1));
                System.out.println(" : ARRAY" + " I: " +  partIndex.get());
                cache.add(parseList(partIndex, parts, controls));
            } else if(first.c() == JsonSyntax.OBJECT_START) {
                controls.set(i, control.substring(first.index() + 1));
                System.out.println(" : OBJECT" + " I: " +  partIndex.get());
                cache.add(parseCache(partIndex, parts, controls));
            } else if(first.c() == JsonSyntax.ARRAY_END) {
                if(first.index() == 0) return cache;
                String value = control.substring(0, first.index());
                cache.add(value);

                if(first.index() + 1 < controls.size())  {
                    control = control.substring(first.index() + 1);
                } else {
                    control = "";
                }
                controls.set(i, control);
                System.out.println(" V: " + value + " OBJ END I: " +  partIndex.get());
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
