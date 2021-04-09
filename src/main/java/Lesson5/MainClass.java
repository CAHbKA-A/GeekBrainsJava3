package Lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {


    public static final int CARS_COUNT = 4;
    private static CyclicBarrier cbForStart;
    private static CountDownLatch countDownForStart;
    private static CountDownLatch countDownForFinish;

    public static int getCarsCount() {
        return CARS_COUNT;
    }

    public static CountDownLatch getCountDownForStart() {
        return countDownForStart;
    }

    public static CountDownLatch getCountDownForFinish() {
        return countDownForFinish;
    }

    public static void main(String[] args) {
        cbForStart = new CyclicBarrier(CARS_COUNT);
        countDownForStart = new CountDownLatch(CARS_COUNT);
        countDownForFinish = new CountDownLatch(CARS_COUNT);
        // Lock lock = new L

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            countDownForStart.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            countDownForFinish.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}