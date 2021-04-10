package Lesson5;

import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private static Integer winCounter = 0;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cbForStart.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            chekFinish();

            MainClass.cbForStart.await();

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();

        }

    }

    private void chekFinish() throws BrokenBarrierException, InterruptedException {
        synchronized (race.getMonitor()) {
            if (winCounter == 0) {
                winCounter++;
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> " + this.name + " Победил!!");
                } else {
                winCounter++;
                System.out.println(winCounter + " место :" + this.name);
            }
        }
    }
}