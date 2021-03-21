package Lesson1;

import java.util.ArrayList;

public class Task_1_2 {
    public static void main(String[] args) {

        BoxArrGeneric arr1 = new BoxArrGeneric(1, 2, 3, 4, 5);
        BoxArrGeneric arr2 = new BoxArrGeneric('1', '2', '3', '4', '5');
        BoxArrGeneric arr3 = new BoxArrGeneric(1, 2f, "String", '4', 5);

//Как логичнее делать? Передавать в статик метод массив?
        BoxArrGeneric.replaceElement(arr1, 0, 2);
        BoxArrGeneric.printArr(arr1);

//или в методе использовать this?  так вроде красивее и короче:
        arr2.replaceElement(0, 2);
        arr2.printArr();

//пробуем вообще разные типы в массив
        arr3.replaceElement(0, 2);
        arr3.printArr();
// Task2
        ArrayList<Object> arrayList = arr1.arrayToArraylist();
        //arrayList = arr1.arrayToArraylist();
        System.out.println(arrayList);

    }
}
