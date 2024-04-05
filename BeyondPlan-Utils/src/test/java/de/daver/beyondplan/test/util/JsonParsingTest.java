package de.daver.beyondplan.test.util;

import de.daver.beyondplan.util.json.JsonObject;
import de.daver.beyondplan.util.json.JsonParser;

public interface JsonParsingTest {

    //region JsonTestStrings
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

    //endregion

    static void main(String[] args) {
        System.out.println("____TEST 1____");
        JsonObject json1 = JsonParser.ofString(TEST1);
        System.out.println(json1);

        System.out.println("____SIMPLE____");
        var jsonSimple = JsonParser.ofString(SIMPLE);
        System.out.println(jsonSimple);

        System.out.println("____BOXED____");
        var jsonBoxed = JsonParser.ofString(BOXED);
        System.out.println(jsonBoxed);

        System.out.println("____ARRAY OF OBJECTS____");
        var jsonArrayOfObjects = JsonParser.ofString(ARRAY_OF_OBJECTS);
        System.out.println(jsonArrayOfObjects);

        System.out.println("____COMPLEX____");
        var jsonComplex = JsonParser.ofString(COMPLEX);
        System.out.println(jsonComplex);

        System.out.println("____EMPTY ARRAY____");
        var jsonEmptyArray = JsonParser.ofString(EMPTY_OBJ_ARRAY);
        System.out.println(jsonEmptyArray);

        System.out.println("____COMMA____");
        var jsonComma = JsonParser.ofString(COMMA_IN_VALUE);
        System.out.println(jsonComma);

        System.out.println("____PARENTHESES____");
        var jsonParentheses = JsonParser.ofString(PARENTHESES_IN_VALUE);
        System.out.println(jsonParentheses);

        System.out.println("____ESCAPE____");
        var jsonEscape = JsonParser.ofString(ESCAPE_IN_VALUE);
        System.out.println(jsonEscape);

        System.out.println("____MIXED SYMBOLS____");
        var jsonMixedSymbols = JsonParser.ofString(MIXED_SYMBOLS);
        System.out.println(jsonMixedSymbols);

        System.out.println("____BOXED WITH SYMBOLS____");
        var jsonBoxedWithSymbols = JsonParser.ofString(BOXED_WITH_SYMBOLS);
        System.out.println(jsonBoxedWithSymbols);

        System.out.println("____FINISHED ALL TESTS____");
    }
}
