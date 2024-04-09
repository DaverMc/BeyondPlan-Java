package de.daver.beyondplan.util.config;

import java.math.BigDecimal;
import java.util.function.Function;


public record ValueGetter(Object value) {

    private  <T> T map(Function<Object, T> function){
        return function.apply(value());
    }

    private  <T> T cast(Class<T> clazz) {
        return map(clazz::cast);
    }

    public String string() {
        return cast(String.class);
    }

    public BigDecimal bigDecimal() {
        return cast(BigDecimal.class);
    }

    public Boolean bool() {
        return cast(Boolean.class);
    }

    public CacheList cacheList() {
        return cast(CacheList.class);
    }

    public Cache cache() {
        return cast(Cache.class);
    }

    /*
    @SuppressWarnings("unchecked")
    private static <N extends Number> N convertNumber(BigDecimal decimal, Class<N> type) {
        if (type == BigDecimal.class) {
            return (N) decimal;
        } else if (type == BigInteger.class) {
            return (N) decimal.toBigInteger();
        } else if (type == Double.class) {
            return (N) Double.valueOf(decimal.doubleValue());
        } else if (type == Float.class) {
            return (N) Float.valueOf(decimal.floatValue());
        } else if (type == Long.class) {
            return (N) Long.valueOf(decimal.longValue());
        } else if (type == Integer.class) {
            return (N) Integer.valueOf(decimal.intValue());
        } else if (type == Short.class) {
            return (N) Short.valueOf(decimal.shortValue());
        } else if (type == Byte.class) {
            return (N) Byte.valueOf(decimal.byteValue());
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + type);
        }
    }
     */

}