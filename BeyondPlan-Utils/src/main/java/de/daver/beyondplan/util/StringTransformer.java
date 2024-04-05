package de.daver.beyondplan.util;

public interface StringTransformer<T> {

    StringTransformer<Integer> INTEGER = Integer::parseInt;
    StringTransformer<Boolean> BOOLEAN = Boolean::parseBoolean;

    T transform(String string);

}
