package Lesson1;

import java.util.List;

public class Task3 {
    public static void main(String[] args) {
        BoxFruit<Orange> boxOrange = new BoxFruit<>();
        BoxFruit<Apple> boxApple = new BoxFruit<>();


        for (int i = 0; i < 12; i++) {
            boxApple.addFruit(new Apple());
        }
        for (int i = 0; i < 8; i++) {
            boxOrange.addFruit(new Orange());
        }

        System.out.println("Вес яблок " + boxApple.getBoxWeight());
        System.out.println("Вес апельсинов, " + boxOrange.getBoxWeight());

        // сравнение
        System.out.println(boxApple.compare(boxOrange));

        //перекладываем

        BoxFruit<Apple> EmptyBox = new BoxFruit<>();
//        System.out.println("1 коробка до "+ boxApple.getBoxWeight());
//        System.out.println("2 коробка до "+ EmptyBox.getBoxWeight());
        boxApple.muvFruits(EmptyBox);
//        System.out.println("1 коробка после "+ boxApple.getBoxWeight());
//        System.out.println("2 коробка после "+ EmptyBox.getBoxWeight());
    }

}
