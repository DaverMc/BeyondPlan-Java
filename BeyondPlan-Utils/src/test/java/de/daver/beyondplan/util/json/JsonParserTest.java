package de.daver.beyondplan.util.json;

import de.daver.beyondplan.util.StringTransformer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    //region JsonTestStrings
    public static final String BASE_TEST = """
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
              "escapedCharacters": "Newline: \n, Tab: \t, Backslash: \\, Quote: ""
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

    @Test
    void baseTestWithVariables() {
        // Parse das BASE_TEST JSON-String zu einem JsonObject
        var jsonObject = JsonParser.ofString(BASE_TEST);

        // Überprüfe und speichere den Namen
        String name = jsonObject.getString("name");
        assertEquals("Max Mustermann", name);

        // Überprüfe und speichere das Alter
        int age = jsonObject.get("alter", StringTransformer.INTEGER);
        assertEquals(30, age);

        // Überprüfe und speichere den Verheirateten-Status
        boolean married = jsonObject.get("verheiratet", StringTransformer.BOOLEAN);
        assertFalse(married);

        // Überprüfe und speichere die Adressen
        JsonArray addresses = jsonObject.getJsonArray("adressen");
        assertNotNull(addresses);
        assertEquals(2, addresses.size());

        // Überprüfe und speichere die erste Adresse
        JsonObject address1 = addresses.getJsonObject(0);
        String address1Street = address1.getString("strasse");
        String address1City = address1.getString("stadt");
        String address1PostalCode = address1.getString("plz");
        assertEquals("Hauptstraße 123", address1Street);
        assertEquals("Musterstadt", address1City);
        assertEquals("12345", address1PostalCode);

        // Überprüfe und speichere die zweite Adresse
        JsonObject address2 = addresses.getJsonObject(1);
        String address2Street = address2.getString("strasse");
        String address2City = address2.getString("stadt");
        String address2PostalCode = address2.getString("plz");
        assertEquals("Nebenstraße 4", address2Street);
        assertEquals("Beispielstadt", address2City);
        assertEquals("67890", address2PostalCode);

        // Überprüfe und speichere die Kontakte
        JsonObject contacts = jsonObject.getJsonObject("kontakte");
        assertNotNull(contacts);
        String email = contacts.getString("email");
        String phone = contacts.getString("telefon");
        assertEquals("max@mustermann.de", email);
        assertEquals("0123-4567890", phone);
    }

    @Test
    void simple() {
        // Parse das JSON-String zu einem JsonObject
        var jsonObject = JsonParser.ofString(SIMPLE);

        // Überprüfe den Namen
        String name = jsonObject.getString("name");
        assertEquals("John Doe", name);

        // Überprüfe das Alter
        int age = jsonObject.get("age", StringTransformer.INTEGER);
        assertEquals(30, age);

        // Überprüfe den Beschäftigungsstatus
        boolean isEmployed = jsonObject.get("isEmployed", StringTransformer.BOOLEAN);
        assertTrue(isEmployed);
    }

    @Test
    void boxed() {
        // Parse das BOXED JSON-String zu einem JsonObject
        var jsonObject = JsonParser.ofString(BOXED);

        // Zugriff auf das "person" Objekt
        JsonObject person = jsonObject.getJsonObject("person");
        assertNotNull(person);

        // Überprüfe und speichere den Namen
        String name = person.getString("name");
        assertEquals("Jane Doe", name);

        // Überprüfe und speichere die Adresse
        JsonObject address = person.getJsonObject("address");
        assertNotNull(address);
        String street = address.getString("street");
        String city = address.getString("city");
        assertEquals("Main St 123", street);
        assertEquals("Anytown", city);

        // Überprüfe und speichere die Hobbys
        JsonArray hobbies = person.getJsonArray("hobbies");
        assertNotNull(hobbies);
        assertEquals(3, hobbies.size());
        assertEquals("Reading", hobbies.getString(0));
        assertEquals("Hiking", hobbies.getString(1));
        assertEquals("Coding", hobbies.getString(2));
    }

    @Test
    void arrayOfObjects() {
        JsonArray jsonArray = JsonParser.arrayOfString(ARRAY_OF_OBJECTS);

        // Erstes Buch
        JsonObject firstBook = jsonArray.getJsonObject(0);
        assertEquals("book", firstBook.getString("type"));
        assertEquals("The Great Gatsby", firstBook.getString("title"));
        assertEquals("F. Scott Fitzgerald", firstBook.getString("author"));

        // Zweites Buch
        JsonObject secondBook = jsonArray.getJsonObject(1);
        assertEquals("book", secondBook.getString("type"));
        assertEquals("Moby Dick", secondBook.getString("title"));
        assertEquals("Herman Melville", secondBook.getString("author"));
    }

    @Test
    void complex() {
        JsonObject jsonObject = JsonParser.ofString(COMPLEX);

        assertEquals("Acme Corp", jsonObject.getString("company"));
        assertEquals(1999, jsonObject.get("founded", StringTransformer.INTEGER));
        assertTrue(jsonObject.get("hasRemoteWork", StringTransformer.BOOLEAN));

        JsonArray employees = jsonObject.getJsonArray("employees");
        assertNotNull(employees);
        assertEquals(2, employees.size());

        // Alice
        JsonObject alice = employees.getJsonObject(0);
        assertEquals("Alice", alice.getString("name"));
        assertEquals("Development", alice.getString("department"));
        assertEquals("09:00", alice.getJsonObject("workingHours").getString("start"));
        assertEquals("17:00", alice.getJsonObject("workingHours").getString("end"));

        // Bob
        JsonObject bob = employees.getJsonObject(1);
        assertEquals("Bob", bob.getString("name"));
        assertEquals("Sales", bob.getString("department"));
        assertEquals("10:00", bob.getJsonObject("workingHours").getString("start"));
        assertEquals("18:00", bob.getJsonObject("workingHours").getString("end"));

        JsonArray officesIn = jsonObject.getJsonArray("officesIn");
        assertNotNull(officesIn);
        assertTrue(officesIn.contains("New York"));
        assertTrue(officesIn.contains("San Francisco"));
        assertTrue(officesIn.contains("Berlin"));
    }

    @Test
    void emptyObjectAndArray() {
        JsonObject jsonObject = JsonParser.ofString(EMPTY_OBJ_ARRAY);

        JsonObject emptyObject = jsonObject.getJsonObject("emptyObject");
        assertNotNull(emptyObject);
        assertTrue(emptyObject.isEmpty());

        JsonArray emptyArray = jsonObject.getJsonArray("emptyArray");
        assertNotNull(emptyArray);
        assertTrue(emptyArray.isEmpty());
    }

    @Test
    void commasInValue() {
        JsonObject jsonObject = JsonParser.ofString(COMMA_IN_VALUE);

        assertEquals("This product, unlike the others, is available in multiple colors.", jsonObject.getString("description"));
        assertEquals("1,999.99", jsonObject.getString("price"));

        JsonArray tags = jsonObject.getJsonArray("tags");
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertTrue(tags.contains("bestseller, limited edition"));
        assertTrue(tags.contains("sale, discount"));
    }

    @Test
    void parenthesisInValue() {
        JsonObject jsonObject = JsonParser.ofString(PARENTHESES_IN_VALUE);

        assertEquals("\"The Catcher in the Rye\" - a novel by J.D. Salinger", jsonObject.getString("title"));
        assertEquals("Considered an \"American classic\" among books.", jsonObject.getString("notes"));
    }

    @Test
    void escapedInValue() {
        JsonObject jsonObject = JsonParser.ofString(ESCAPE_IN_VALUE);

        assertEquals("Newline: \\n, Tab: \\t, Backslash: \\\\", jsonObject.getString("escapedCharacters"));
    }

    @Test
    void mixedSymbols() {
        JsonObject jsonObject = JsonParser.ofString(MIXED_SYMBOLS);

        assertEquals("This string contains a mix of special characters: commas, quotes, braces {}, and brackets [].", jsonObject.getString("complexString"));
        assertEquals("x > y ? x : y; // returns x if x is greater than y", jsonObject.getString("mathExpression"));
    }

    @Test
    void boxedWithSymbols() {
        JsonObject jsonObject = JsonParser.ofString(BOXED_WITH_SYMBOLS).getJsonObject("book");

        assertEquals("Understanding JSON: {A Beginner's Guide}", jsonObject.getString("title"));

        JsonArray chapters = jsonObject.getJsonArray("chapters");
        assertNotNull(chapters);
        assertEquals(2, chapters.size());
        assertEquals("Basics of JSON", chapters.getString(0));
        assertEquals("Advanced Techniques, Tips & Tricks", chapters.getString(1));

        assertEquals("20.50", jsonObject.getString("price"));
        assertEquals("In stock, ships in 3-5 days", jsonObject.getString("availability"));
    }
}