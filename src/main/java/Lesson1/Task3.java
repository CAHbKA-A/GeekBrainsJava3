package Lesson1;

import java.util.List;

public class Task3 {
    public static void main(String[] args) {
        BoxFruit<Orange> boxOrange = new BoxFruit();
        BoxFruit<Apple> boxApple = new BoxFruit();


        for (int i = 0; i < 12; i++) {
            boxApple.addFruit(new Apple());
        }
        for (int i = 0; i < 8; i++) {
            boxOrange.addFruit(new Orange());
        }

        System.out.println("В ящике " + boxApple.getBoxFruitCount() + " яблок, весом " + boxApple.getBoxWight());
        System.out.println("В ящике " + boxOrange.getBoxFruitCount() + " апельсинов, весом " + boxOrange.getBoxWight());

        System.out.println(boxApple.compare(boxOrange));


    }

    static <T> T addFruit(List<T> list) {

        return null;//BoxFruit;
    }
}
