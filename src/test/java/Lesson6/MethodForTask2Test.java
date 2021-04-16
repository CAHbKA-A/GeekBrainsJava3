package Lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class MethodForTask2Test {
    private MethodForTask2 methodForTask2;

    @BeforeEach
    void init() {
        methodForTask2 = new MethodForTask2();
    }


    public static List<Object[]> arrays2test1() {
        List list4test = Arrays.asList(new Object[][]{
                {new int[]{1, 4, 11, 13, 51, 16}, new int[]{11, 13, 51, 16}},
                {new int[]{1, 4, 1, 1, 4, 1}, new int[]{1}},
                {new int[]{1, 1, 1, 1, 7, 4}, new int[]{}},
                {new int[]{4, 1, 4, 6, 7, 8}, new int[]{6, 7, 8}}
        });
        return list4test;
    }

    @ParameterizedTest
    @MethodSource("arrays2test1")
    void chekOneAndFour(int[] a, int[] res) {

        Assertions.assertArrayEquals(res, methodForTask2.subArray(a));
    }


    @Test
    void subArrayHaveNo4() {
        int[] k = {7, 46, 5, 8, 8, 9, 5, 3, 67, 3, 3, 6};
        Assertions.assertThrows(RuntimeException.class, () -> methodForTask2.subArray(k));
    }
}