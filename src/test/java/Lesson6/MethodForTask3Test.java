package Lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

class MethodForTask3Test {
    private MethodForTask3 methodForTask3;

    @BeforeEach
    public void init() {
        methodForTask3 = new MethodForTask3();
    }

    @ParameterizedTest
    @MethodSource("arrays2test")
    void chekOneAndFour(int[] a, boolean has) {
        MethodForTask3 methodForTask3 = new MethodForTask3();
        Assertions.assertEquals(has, methodForTask3.chekOneAndFour(a));
    }

    public static List<Object[]> arrays2test() {
        List list4test = Arrays.asList(new Object[][]{
                {new int[]{1, 1, 1, 1, 1, 1}, false},
                {new int[]{1, 4, 1, 1, 1, 1}, true},
                {new int[]{1, 1, 1, 4, 1, 1}, true},
                {new int[]{4, 4, 4, 4, 4}, false},
                {new int[]{1, 1, 1, 1, 1}, false},
                {new int[]{1, 1, 1, 4, 1, 1}, true}
        });
        return list4test;
    }
}


