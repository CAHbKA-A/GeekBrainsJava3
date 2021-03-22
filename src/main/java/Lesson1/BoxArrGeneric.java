package Lesson1;

import java.util.ArrayList;
import java.util.Collections;

public class BoxArrGeneric {
    private Object[] obj;

    public BoxArrGeneric(Object... obj) {
        this.obj = obj;
    }

    public Object[] getObj() {
        return obj;
    }

    public void setObj(Object[] obj) {
        this.obj = obj;
    }


    public static BoxArrGeneric replaceElement(BoxArrGeneric boxArrGeneric, int i, int j) {
        Object tmp = boxArrGeneric.obj[i];
        boxArrGeneric.obj[i] = boxArrGeneric.obj[j];
        boxArrGeneric.obj[j] = tmp;
        return boxArrGeneric;
    }



    public static void printArr(BoxArrGeneric boxArrGeneric) {
        for (Object element : boxArrGeneric.obj) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public ArrayList<Object>  arrayToArraylist() {
        ArrayList <Object> arrList = new ArrayList<>();
        Collections.addAll(arrList, this.obj);
        return arrList;
    }

}
