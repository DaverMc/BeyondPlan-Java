package de.daver.beyondplan.util.json;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class JsonObject {

    private final Map<String, Object> values;

    public JsonObject() {
        this.values = new HashMap<>();
    }


    public static JsonObject parseJson(String json) {
        JsonObject root = new JsonObject();
        String[] parts = json.split(",");
        for(int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return (JsonObject) root.parseValue(parts);
    }

    private Object parseValue(String[] parts) {
        JsonObject root = new JsonObject();
        for(int i = 0; i < parts.length; i++) {
            String part = parts[i];
            //System.out.println(part);
            String key = part.split(":")[0];
            if(part.contains("{")) {
                System.out.println("DOWN");
                String[] newParts = new String[parts.length - i];

                System.arraycopy(parts, i, newParts, 0, newParts.length);
                int first = part.indexOf('{');
                newParts[0] = part.substring(0, first) + part.substring(first + 1);
                var value = parseValue(newParts);
                root.values.put(key, value);
            }
            else if(part.contains("}")) {
                System.out.println("UP");
                int first = part.indexOf('}');
                parts[i] = part.substring(0, first) + part.substring(first + 1);
                return root;
            }
            else {
                String val = part.replace(key, part.replace(key, ""));
                System.out.println("K:" + key + " | V:" + val);
                root.values.put(key, val);
            }
        }
        return root;
    }

    public void print(boolean deep) {
        for(String key : values.keySet()) {
            var value = values.get(key);
            if(value instanceof JsonObject json) {
                if(deep) json.print(true);
            }else System.out.println(key + " | " + values.get(key));
        }
    }


    private String removeUnnecessarySpace(String s){
        return s.replaceAll(" ", ""); //TODO Nur zwishcne werten
    }

    private Map<String, JsonValue<?>> r(JsonObject root, String[] leftParts) {
        for(int i = 0; i < leftParts.length; i++) {
            String part = removeUnnecessarySpace(leftParts[i]);

            if(part.contains("{")) {
                int count = countChars(part, '{');
                for(int j = 0; j < count; j++) System.out.println("Open");
                part = part.replace("{" , "");
                String[] newLeftOvers = new String[leftParts.length - i];
                System.arraycopy(leftParts, i, newLeftOvers, 0, newLeftOvers.length);
            }
            if(part.contains("}")) {
                int count = countChars(part, '}');
                for(int j = 0; j < count; j++) System.out.println("Close");
                part = part.replace("}" , "");
            }

            if(part.contains("[")) {
                int count = countChars(part, '[');
                for(int j = 0; j < count; j++) System.out.println("Array Open");
                part = part.replace("]" , "");
            }

            if(part.contains("]")) {
                int count = countChars(part, ']');
                for(int j = 0; j < count; j++) System.out.println("Array Close");
                part = part.replace("]" , "");
            }
            System.out.println(part);
        }
        return null;
    }

    private int countChars(String s, char c) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) if(s.charAt(i) == c) count++;
        return count;
    }
/*
    public <T> T get(String key) {
        return (T) values.get(key).value();
    }
 */

    public static JsonObject ofHttpResponse(HttpResponse<String> response) {
        String jsonString = response.body();
        return JsonObject.parseJson(jsonString);
    }

}
