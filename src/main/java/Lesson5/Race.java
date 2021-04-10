package Lesson5;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private ArrayList<Stage> stages;

    private Object monitor;
    public Object getMonitor() {
        return monitor;
    }
    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.monitor = new Object();
    }
}