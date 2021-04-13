package Lesson6;
/*Написать метод, которому в качестве аргумента передается
не пустой одномерный целочисленный массив. Метод должен вернуть новый массив,
который получен путем вытаскивания из исходного массива элементов, и
дущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
 иначе в методе необходимо выбросить RuntimeException. Написать набор тестов для этого метода
(по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].*/

import java.util.Arrays;

public class MethodForTask2 {

   public  int[] subArray(int[] array) {
        int index = -1;
        for (int i = array.length -1; i >= 0; i--) {
            if (array[i] == 4) {
                index = i;
                break;
            }
        }
        if ((index == -1)) {
            throw new RuntimeException();
        }
        return Arrays.copyOfRange(array, index+1, array.length );
    }


}
