package de.daver.beyondplan.util.config;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public record ListGetter(List<Object> value) {

    public <T> List<T> cast(Class<T> clazz) {
        return map(clazz::cast);
    }

    public <T> List<T> map(Function<Object, T> transformer) {
        return value().stream().map(transformer).toList();
    }

    public Stream<Object> stream() {
        return value().stream();
    }

    public <T> Stream<T> stream(Function<Object, T> transformer) {
        return stream().map(transformer);
    }

    public <T> Stream<T> stream(Class<T> clazz) {
        return stream(clazz::cast);
    }

}
