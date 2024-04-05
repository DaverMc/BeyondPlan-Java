package de.daver.beyondplan.core;

import de.daver.beyondplan.core.json.JsonObject;
import de.daver.beyondplan.core.json.JsonParser;

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

    public static final String COMMA_IN_VALUE = """
            {
              "description": "This product, unlike the others, is available in multiple colors.",
              "price": "1,999.99",
              "tags": ["bestseller, limited edition", "sale, discount"]
            }
            """;

    public static final String PARENTHESES_IN_VALUE = """
            {
              "title": "\\"The Catcher in the Rye\\" - a novel by J.D. Salinger",
              "notes": "Considered an \\"American classic\\" among books."
            }
            """;

    public static final String ESCAPE_IN_VALUE = """
            {
              "escapedCharacters": "Newline: \\\\n, Tab: \\\\t, Backslash: \\\\\\\\, Quote: \\\\\\""
            }
            """;

    public static final String MIXED_SYMBOLS = """
            {
              "complexString": "This string contains a mix of special characters: commas, quotes, braces {}, and brackets [].",
              "mathExpression": "x > y ? x : y; // returns x if x is greater than y"
            }
            """;

    public static final String BOXED_WITH_SYMBOLS = """
            {
              "book": {
                "title": "Understanding JSON: {A Beginner's Guide}",
                "chapters": [
                  "Basics of JSON",
                  "Advanced Techniques, Tips & Tricks"
                ],
                "price": "20.50",
                "availability": "In stock, ships in 3-5 days"
              }
            }
            """;

    public static void main(String[] args) {
        JsonObject json1 = JsonParser.ofString(TEST1);
        json1.print();

        var jsonSimple = JsonParser.ofString(SIMPLE);
        jsonSimple.print();

        var jsonBoxed = JsonParser.ofString(BOXED);
        jsonBoxed.print();

        var jsonArrayOfObjects = JsonParser.ofString(ARRAY_OF_OBJECTS);
        jsonArrayOfObjects.print();

        var jsonComplex = JsonParser.ofString(COMPLEX);
        jsonComplex.print();

        var jsonEmptyArray = JsonParser.ofString(EMPTY_OBJ_ARRAY);
        jsonEmptyArray.print();

        var jsonComma = JsonParser.ofString(COMMA_IN_VALUE);
        jsonComma.print();

        var jsonParentheses = JsonParser.ofString(PARENTHESES_IN_VALUE);
        jsonParentheses.print();

        var jsonEscape = JsonParser.ofString(ESCAPE_IN_VALUE);
        jsonEscape.print();

        var jsonMixedSymbols = JsonParser.ofString(MIXED_SYMBOLS);
        jsonMixedSymbols.print();

        var jsonBoxedWithSymbols = JsonParser.ofString(BOXED_WITH_SYMBOLS);
        jsonBoxedWithSymbols.print();

    }
}