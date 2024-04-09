package de.daver.beyondplan.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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

    static boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

    record ReturnValue<V>(V value, boolean success) {}

    static ReturnValue<Boolean> parseBoolean(String str) {
        if(!isBoolean(str)) return new ReturnValue<>(null, false);
        return new ReturnValue<>(Boolean.parseBoolean(str), true);
    }

    static ReturnValue<BigDecimal> parseNumber(String str) {
        try {
            return new ReturnValue<>(new BigDecimal(str), true);
        } catch (NumberFormatException e) {
            return new ReturnValue<>(null, false);
        }
    }

}
