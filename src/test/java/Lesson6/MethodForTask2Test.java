package Lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodForTask2Test {
    private MethodForTask2 methodForTask2;

    @BeforeEach
    void init() {
        methodForTask2 = new MethodForTask2();
    }

    @Test
    void subArraySimpleTest() {
        //  MethodForTask2 methodForTask2 = new MethodForTask2();
        int[] k = {4, 46, 4, 8, 4, 9, 9};
        int[] res = {9, 9};
        Assertions.assertArrayEquals(res, methodForTask2.subArray(k));
    }

    @Test
    void subArraySimple2() {
        int[] k = {7, 46, 5, 8, 8, 9, 4, 3, 67, 3, 3, 6};
        int[] res = {3, 67, 3, 3, 6};
        Assertions.assertArrayEquals(res, methodForTask2.subArray(k));
    }

    @Test
    void subArrayHaveNo4() {
        int[] k = {7, 46, 5, 8, 8, 9, 5, 3, 67, 3, 3, 6};
        Assertions.assertThrows(RuntimeException.class, () -> methodForTask2.subArray(k));
    }

    @Test
    void subArray4AtLastPosition() {
        int[] k = {7, 46, 5, 8, 4, 9, 4};
        int[] res = {};
        Assertions.assertArrayEquals(res, methodForTask2.subArray(k));
    }

    //для попрбовать
    @Test
    void subArrayTry() {
        //  MethodForTask2 methodForTask2 = new MethodForTask2();
        int[] k = {7, 46, 5, 4, 4, 4, 5};
        int[] res = {4};
        Assertions.assertNotEquals(res, methodForTask2.subArray(k));
    }

    //заведомо кривой тест (непроходмиый)
    @Test
    void subArrayBadTest() {
        //  MethodForTask2 methodForTask2 = new MethodForTask2();
        int[] k = {7, 46, 5, 4, 4, 4, 5};
        int[] res = {4};
        Assertions.assertEquals(res, methodForTask2.subArray(k));
    }
}