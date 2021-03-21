package Lesson1;

import java.util.ArrayList;

public class BoxFruit<T extends Fruits> {

    private ArrayList<T> fruits;
    private double boxWight;
    private int boxFruitCount;

    public BoxFruit() {
        this.fruits = new ArrayList<>();
    }

    public double getBoxWight() {
        return boxWight;
    }

    public int getBoxFruitCount() {
        return boxFruitCount;
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
        this.boxWight = this.boxWight + fruit.getWeight();
        this.boxFruitCount++;
    }

    public boolean compare(BoxFruit <?> compareBox) {
        return (Math.abs(compareBox.getBoxWight() - this.getBoxWight())) < 0.00001;
    }

}
