package de.daver.beyondplan.util;

import java.util.ArrayList;
import java.util.List;

public interface StringUtils {

    static String removeFirst(char c, String s) {
        int i = s.indexOf(c);
        return s.substring(0, i) + s.substring(i + 1);
    }

    static String removeLast(char c, String s) {
        int i = s.lastIndexOf(c);
        return s.substring(0, i) + s.substring(i + 1);
    }

    static IndexedCharacter findFirstAppearence(String s, char... ch) {
        return findFirstAppearence(s, 0, ch);
    }

    static IndexedCharacter findFirstAppearence(String s, int startIndex, char...ch) {
        for(int i = startIndex; i < s.length(); i++) {
            char c = s.charAt(i);
            for(char cc : ch) {
                if(c == cc) return new IndexedCharacter(i, c);
            }
        }
        return null;
    }

    static List<Integer> indexes(String s, char c) {
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < s.length(); i++) if(s.charAt(i) == c) indexes.add(i);
        return indexes;
    }

    record IndexedCharacter(int index, char c) {}

}
