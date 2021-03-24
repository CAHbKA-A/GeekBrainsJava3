package Lesson1;

import java.util.ArrayList;

public class BoxFruit<T extends Fruits> {

    private ArrayList<T> fruits;


    public BoxFruit() {
        this.fruits = new ArrayList<>();
    }


    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public double getBoxWeight() {
        if (fruits.size() != 0) {
            return fruits.size() * fruits.get(0).getWeight();
        } else return 0;
    }


    public boolean compare(BoxFruit<?> compareBox) {
        return (Math.abs(compareBox.getBoxWeight() - this.getBoxWeight())) < 0.00001;
    }

    public void muvFruits(BoxFruit<T> EmptyBox) {

        if  (this == EmptyBox) return;
        EmptyBox.fruits.addAll(fruits);

        this.fruits.clear();
    }

}
