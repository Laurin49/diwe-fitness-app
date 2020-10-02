package info.diwe.fitnessapp.bootstrap;

import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ExerciseService exerciseService;


    @Override
    public void run(String... args) throws Exception {
        List<Exercise> exercises = new ArrayList<>();
        exerciseService.readExercises().iterator().forEachRemaining(exercises::add);
        if (exercises.size() == 0) {
            initExerciseData();
        }
    }

    private void initExerciseData() {
        Exercise ex = new Exercise("Bankdrücken");
        exerciseService.createExercise(ex);
        ex = new Exercise("Schulterdrücken");
        exerciseService.createExercise(ex);
        ex = new Exercise("Seitheben");
        exerciseService.createExercise(ex);
        ex = new Exercise("Seitheben vorg.");
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern vorg. SZ-H");
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern vorg. 1-Arm");
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern Seilzug");
        exerciseService.createExercise(ex);
        ex = new Exercise("Bizeps-Curl SZ-H");
        exerciseService.createExercise(ex);
    }
}
