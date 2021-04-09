package Lesson5;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
//

public class Race {
    private CopyOnWriteArrayList<Stage> stages ;

    public CopyOnWriteArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new CopyOnWriteArrayList<Stage>(Arrays.asList(stages));
    }
}