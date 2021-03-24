package Lesson1;

import java.util.ArrayList;

public class Task_1_2 {
    public static void main(String[] args) {

        BoxArrGeneric arr1 = new BoxArrGeneric(1, 2, 3, 4, 5);
        BoxArrGeneric arr3 = new BoxArrGeneric(1, 2f, "String", '4', 5);


        BoxArrGeneric.replaceElement(arr1, 0, 2);
        BoxArrGeneric.printArr(arr1);

        BoxArrGeneric.replaceElement(arr3, 0, 2);
        BoxArrGeneric.printArr(arr3);


// Task2
        ArrayList<Object> arrayList = arr1.arrayToArraylist();

        System.out.println(arrayList);

    }
}
