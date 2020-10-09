package info.diwe.fitnessapp.bootstrap;

import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.Workout;
import info.diwe.fitnessapp.model.enums.MuscleGroup;
import info.diwe.fitnessapp.service.ExerciseService;
import info.diwe.fitnessapp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private WorkoutService workoutService;


    @Override
    public void run(String... args) throws Exception {
        List<Exercise> exercises = new ArrayList<>();
        exerciseService.readExercises().iterator().forEachRemaining(exercises::add);
        if (exercises.size() == 0) {
            initExerciseData();
        }
        List<Workout> workouts = new ArrayList<>();
        workoutService.readWorkouts().iterator().forEachRemaining(workouts::add);
        if (workouts.size() == 0) {
            initWorkoutData();
        }

    }

    private void initExerciseData() {
        Exercise ex = new Exercise("Bankdrücken", MuscleGroup.Brust);
        exerciseService.createExercise(ex);
        ex = new Exercise("Schulterdrücken", MuscleGroup.Schultern);
        exerciseService.createExercise(ex);
        ex = new Exercise("Seitheben", MuscleGroup.Schultern);
        exerciseService.createExercise(ex);
        ex = new Exercise("Seitheben vorg.", MuscleGroup.Schultern);
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern vorg. SZ-H", MuscleGroup.Rücken);
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern vorg. 1-Arm", MuscleGroup.Rücken);
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern Seilzug", MuscleGroup.Rücken);
        exerciseService.createExercise(ex);
        ex = new Exercise("Bizeps-Curl SZ-H", MuscleGroup.Bizeps);
        exerciseService.createExercise(ex);
    }

    private void initWorkoutData() {
        Workout w1 = new Workout("Rücken", LocalDate.now().minusDays(5));
        workoutService.createWorkout(w1);
        w1 = new Workout("Brust", LocalDate.now().minusWeeks(2));
        workoutService.createWorkout(w1);
        w1 = new Workout("Bizeps", LocalDate.now());
        workoutService.createWorkout(w1);
        w1 = new Workout("Trizep", LocalDate.now());
        workoutService.createWorkout(w1);
        w1 = new Workout("Schulter", LocalDate.now().minusDays(4));
        workoutService.createWorkout(w1);
        w1 = new Workout("Bauch", LocalDate.now().minusDays(4));
        workoutService.createWorkout(w1);
        w1 = new Workout("Bein", LocalDate.now().minusDays(3));
        workoutService.createWorkout(w1);
        w1 = new Workout("Rücken", LocalDate.now().minusDays(3));
        workoutService.createWorkout(w1);
    }
}
