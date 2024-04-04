package de.daver.beyondplan.util;


import java.util.Date;

public interface ObjectTransformer<T> {

    ObjectTransformer<Integer> INT = object -> (Integer) object;
    ObjectTransformer<String> STRING = String::valueOf;
    ObjectTransformer<Date> DATE = object -> (Date) object;

    T transform(Object object);

}
