package Lesson1;

import java.util.ArrayList;

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

    public void replaceElement(int i, int j) {
        Object tmp = this.obj[i];
        this.obj[i] = this.obj[j];
        this.obj[j] = tmp;
    }


    public static BoxArrGeneric replaceElement(BoxArrGeneric boxArrGeneric, int i, int j) {
        Object tmp = boxArrGeneric.obj[i];
        boxArrGeneric.obj[i] = boxArrGeneric.obj[j];
        boxArrGeneric.obj[j] = tmp;
        return boxArrGeneric;
    }


    public void printArr() {
        for (Object element : this.obj) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static void printArr(BoxArrGeneric boxArrGeneric) {
        for (Object element : boxArrGeneric.obj) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public ArrayList<Object>  arrayToArraylist() {
        ArrayList <Object> arrList = new ArrayList<>();
        for (Object o : this.obj) {
           arrList.add(o);
        }
        return arrList;
    }

}
