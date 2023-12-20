package com.nhnacademy.aiot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {


    private static Stream<Arguments> divideTestCase() {
        return Stream.of(
                Arguments.of(List.of(7, 9, 5)),
                Arguments.of(List.of(5, 2, 8)),
                Arguments.of(List.of(4, 6, 3))
        );
    }

    @MethodSource("divideTestCase")
    @ParameterizedTest
    void divideArrayTest(List<Integer> input) {
        int[] ints = input.stream().mapToInt(x -> x).toArray();

        DividedArrays actual = MergeSort.divide(ints);

        int len = actual.getLeftArray().length + actual.getRightArray().length;

        assertEquals(ints.length, len);
    }

    private static Stream<Arguments> mergeArrayTestCase() {
        return Stream.of(
                Arguments.of(List.of(7, 9), List.of(5)),
                Arguments.of(List.of(5, 8), List.of(2)),
                Arguments.of(List.of(4, 6), List.of(3))
        );
    }

    @MethodSource("mergeArrayTestCase")
    @ParameterizedTest
    void mergeArrayTest(List<Integer> left, List<Integer> right) {
        int[] leftArray = left.stream().mapToInt(x -> x).toArray();
        int[] rightArray = right.stream().mapToInt(x -> x).toArray();

        int expectedLength = leftArray.length + rightArray.length;

        int[] actual = MergeSort.merge(leftArray, rightArray);

        //assertion
        assertEquals(expectedLength, actual.length);

        int temp = Integer.MIN_VALUE;

        for (int i : actual) {
            assertFalse(temp > i);
            temp = i;
        }
    }


    private static Stream<Arguments> mergeSortTestCase() {
        return Stream.of(
                Arguments.of(List.of(42, 17, 8, 64, 23, 91, 55, 3, 36, 70),
                        List.of(3, 8, 17, 23, 36, 42, 55, 64, 70, 91)),
                Arguments.of(List.of(61, 33, 27, 6, 14, 78, 50, 38, 59, 68),
                        List.of(6, 14, 27, 33, 38, 50, 59, 61, 68, 78)),
                Arguments.of(List.of(21, 96, 58, 13, 86, 4, 24, 66, 75, 77),
                        List.of(4, 13, 21, 24, 58, 66, 75, 77, 86, 96)),
                Arguments.of(List.of(44, 34, 51, 69, 94, 56, 62, 87, 81, 89),
                        List.of(34, 44, 51, 56, 62, 69, 81, 87, 89, 94))
        );
    }

    @MethodSource("mergeSortTestCase")
    @ParameterizedTest
    void mergeSortTest(List<Integer> input, List<Integer> expect) {
        int[] inputArray = input.stream().mapToInt(x -> x).toArray();
        int[] expectArray = expect.stream().mapToInt(x -> x).toArray();

        int[] actual = MergeSort.mergeSort(inputArray);

        assertArrayEquals(expectArray, actual);
    }

}
