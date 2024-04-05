package de.daver.beyondplan.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void removeFirstA() {
        String str = "That is an important test";
        assertEquals(StringUtils.removeFirst('a', str), "Tht is an important test");
    }


    @Test
    void removeLastA() {
        String str = "That is an important test";
        assertEquals(StringUtils.removeLast('a', str), "That is an importnt test");
    }

}