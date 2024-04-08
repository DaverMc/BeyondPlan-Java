package de.daver.beyondplan.util.config.format;

public class JsonSyntax {

    public static final char OBJECT_START = '{';
    public static final char OBJECT_END = '}';
    public static final char ARRAY_START = '[';
    public static final char ARRAY_END = ']';
    public static final char STRING_DELIMITER = '"';
    public static final char ELEMENT_SEPARATOR = ',';
    public static final char KEY_VALUE_SEPARATOR = ':';
    public static final char NEGATIVE_SIGN = '-';

    public static final char[] JSON_SYNTAX_CHARACTERS = {
            OBJECT_START, OBJECT_END, ARRAY_START, ARRAY_END,
            STRING_DELIMITER, ELEMENT_SEPARATOR, KEY_VALUE_SEPARATOR, NEGATIVE_SIGN
    };
}
