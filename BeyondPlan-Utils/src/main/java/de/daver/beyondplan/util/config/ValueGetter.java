package de.daver.beyondplan.util.config;

import java.util.function.Function;


public record ValueGetter(Object value) {

    public <T> T map(Function<Object, T> function){
        return function.apply(value());
    }


    public <T> T cast(Class<T> clazz) {
        return map(clazz::cast);
    }

}