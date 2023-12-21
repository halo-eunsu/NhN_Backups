package com.nhnacademy.aiot;

import static org.junit.Assert.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MainTest {
    private static Stream<Arguments> testcases() {
        return Stream.of(
            Arguments.of("759618324", "123456789"),
            Arguments.of("2641739", "1234679"),
            Arguments.of("34738230189", "01233347889")
        );
    }
    
    @ParameterizedTest(name = "Quick sort {0} --> {1}")
    @MethodSource("testcases")
    @DisplayName("퀵 정렬 테스트")
    void mainTest(String before, String after) {
        assertEquals(Main.sort(before), after);
    }
}