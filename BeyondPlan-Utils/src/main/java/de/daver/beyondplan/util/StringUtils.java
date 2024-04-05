package de.daver.beyondplan.util;

public interface StringUtils {

    static String removeFirst(char c, String s) {
        int i = s.indexOf(c);
        return s.substring(0, i) + s.substring(i + 1);
    }

    static String removeLast(char c, String s) {
        int i = s.lastIndexOf(c);
        return s.substring(0, i) + s.substring(i + 1);
    }

}
