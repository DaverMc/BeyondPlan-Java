package de.daver.beyondplan.core;

import de.daver.beyondplan.core.json.JsonArray;
import de.daver.beyondplan.core.json.JsonObject;

public class TestJsonParsing {

    public static final String TEST1 = """
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

    public static final String SIMPLE = """
            {
              "name": "John Doe",
              "age": 30,
              "isEmployed": true
            }
            """;

    public static final String BOXED = """
            {
              "person": {
                "name": "Jane Doe",
                "address": {
                  "street": "Main St 123",
                  "city": "Anytown"
                },
                "hobbies": ["Reading", "Hiking", "Coding"]
              }
            }
            """;

    public static final String ARRAY_OF_OBJECTS = """
            [
              {
                "type": "book",
                "title": "The Great Gatsby",
                "author": "F. Scott Fitzgerald"
              },
              {
                "type": "book",
                "title": "Moby Dick",
                "author": "Herman Melville"
              }
            ]
            """;

    public static final String COMPLEX = """
            {
              "company": "Acme Corp",
              "employees": [
                {
                  "name": "Alice",
                  "department": "Development",
                  "workingHours": {
                    "start": "09:00",
                    "end": "17:00"
                  }
                },
                {
                  "name": "Bob",
                  "department": "Sales",
                  "workingHours": {
                    "start": "10:00",
                    "end": "18:00"
                  }
                }
              ],
              "founded": 1999,
              "hasRemoteWork": true,
              "officesIn": ["New York", "San Francisco", "Berlin"]
            }
            """;

    public static final String EMPTY_OBJ_ARRAY = """
            {
              "emptyObject": {},
              "emptyArray": []
            }
            """;

    public static void main(String[] args) {
        JsonObject json1 =JsonObject.parse(TEST1);
        json1.print();

        var jsonSimple = JsonObject.parse(SIMPLE);
        jsonSimple.print();

        var jsonBoxed = JsonObject.parse(BOXED);
        jsonBoxed.print();

        var jsonArrayOfObjects = JsonObject.parse(ARRAY_OF_OBJECTS);
        jsonArrayOfObjects.print();

        var jsonComplex = JsonObject.parse(COMPLEX);
        jsonComplex.print();

        var jsonEmptyArray = JsonObject.parse(EMPTY_OBJ_ARRAY);
        jsonEmptyArray.print();
    }

    private static void simpleTest() {
        String json = """
                {
                  "name": "John Doe",
                  "age": 30,
                  "isEmployed": true
                }
                """;
    }
}