package de.daver.beyondplan.core;

import de.daver.beyondplan.core.json.JsonArray;
import de.daver.beyondplan.core.json.JsonObject;

public class TestJsonParsing {

    public static void main(String[] args) {
        var json = """
                {
                    "name": "Max Mustermann",
                    "alter": 30,
                    "verheiratet": false
                }
                """;
        String s = """
                {
                  "name": "Max Mustermann",
                  "alter": 30,
                  "verheiratet": false,
                  "adressen": [
                    {
                      "strasse": "Hauptstraße 123",
                      "stadt": "Musterstadt",
                      "plz": "12345"
                    },
                    {
                      "strasse": "Nebenstraße 4",
                      "stadt": "Beispielstadt",
                      "plz": "67890"
                    }
                  ],
                  "kontakte": {
                    "email": "max@mustermann.de",
                    "telefon": "0123-4567890"
                  }
                }
                """;
        JsonObject jsonObject =JsonObject.parse(s);
        jsonObject.print();
        jsonObject.getString("name");
        jsonObject.getString("alter");
        jsonObject.getString("verheiratet");

        JsonArray array = jsonObject.getJsonArray("adressen");

        var v1 = array.getJsonObject(0);
        v1.getString("strasse");
        v1.getString("stadt");
        v1.getString("plz");

        var v2 = array.getJsonObject(1);
        v2.getString("strasse");
        v2.getString("stadt");
        v2.getString("plz");

        var o3 = jsonObject.getJsonObject("kontakte");
        o3.getString("email");
        o3.getString("telefon");
    }
}