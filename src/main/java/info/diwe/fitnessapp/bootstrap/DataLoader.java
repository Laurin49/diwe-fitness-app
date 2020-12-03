package info.diwe.fitnessapp.bootstrap;

import info.diwe.fitnessapp.exception.ResourceNotFoundException;
import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.Practice;
import info.diwe.fitnessapp.model.Workout;
import info.diwe.fitnessapp.model.enums.MuscleGroup;
import info.diwe.fitnessapp.service.ExerciseService;
import info.diwe.fitnessapp.service.PracticeService;
import info.diwe.fitnessapp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private PracticeService practiceService;

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
        List<Practice> practices = new ArrayList<>();
        practiceService.readPractices().iterator().forEachRemaining(practices::add);
        if (practices.size() == 0) {
            initPracticeData();
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
        ex = new Exercise("Rudern Maschine", MuscleGroup.Rücken);
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern vorg. 1-Arm", MuscleGroup.Rücken);
        exerciseService.createExercise(ex);
        ex = new Exercise("Rudern Seilzug", MuscleGroup.Rücken);
        exerciseService.createExercise(ex);
        ex = new Exercise("Langhantel Bizeps-Curls", MuscleGroup.Bizeps);
        exerciseService.createExercise(ex);
        ex = new Exercise("Scott-Curls K.H.", MuscleGroup.Bizeps);
        exerciseService.createExercise(ex);
        ex = new Exercise("Hammer-Curls K.H.", MuscleGroup.Bizeps);
        exerciseService.createExercise(ex);
        ex = new Exercise("Konzentrations-Curls K.H.", MuscleGroup.Bizeps);
        exerciseService.createExercise(ex);
    }

    private void initWorkoutData() {
        Workout w1 = new Workout("Rückentraining", LocalDate.now().minusDays(5));
        workoutService.createWorkout(w1);
        w1 = new Workout("Brusttraining", LocalDate.now().minusWeeks(2));
        workoutService.createWorkout(w1);
        w1 = new Workout("Bizepstraining", LocalDate.now());
        workoutService.createWorkout(w1);
        w1 = new Workout("Trizepstraining", LocalDate.now());
        workoutService.createWorkout(w1);
        w1 = new Workout("Schultertraining", LocalDate.now().minusDays(4));
        workoutService.createWorkout(w1);
        w1 = new Workout("Bauchtraining", LocalDate.now().minusDays(4));
        workoutService.createWorkout(w1);
        w1 = new Workout("Beintraining", LocalDate.now().minusDays(3));
        workoutService.createWorkout(w1);
        w1 = new Workout("Rückentraining", LocalDate.now().minusDays(3));
        workoutService.createWorkout(w1);
    }

    private void initPracticeData() {
        Optional<Workout> optWorkout = workoutService.findByNameAndDatum("Schultertraining", LocalDate.now().minusDays(4));
        if (optWorkout.isPresent()) {
            Practice p1 = new Practice(exerciseService.findByName("Schulterdrücken").get(), 4);
            optWorkout.get().getPracticeList().add(p1);
            workoutService.updateWorkout(optWorkout.get());
            p1.setWorkout(optWorkout.get());
            practiceService.createPractice(p1);

            Practice p2 = new Practice(exerciseService.findByName("Seitheben").get(), 3);
            optWorkout.get().getPracticeList().add(p2);
            workoutService.updateWorkout(optWorkout.get());
            p2.setWorkout(optWorkout.get());
            practiceService.createPractice(p2);

            Practice p3 = new Practice(exerciseService.findByName("Seitheben vorg.").get(), 3);
            optWorkout.get().getPracticeList().add(p3);
            workoutService.updateWorkout(optWorkout.get());
            p3.setWorkout(optWorkout.get());
            practiceService.createPractice(p3);

        } else {
            throw new ResourceNotFoundException("initPracticeDate: Workout nicht gefunden");
        }

        optWorkout = workoutService.findByNameAndDatum("Rückentraining", LocalDate.now().minusDays(3));
        if (optWorkout.isPresent()) {

            Practice p4 = new Practice(exerciseService.findByName("Rudern Maschine").get(), 5);
            optWorkout.get().getPracticeList().add(p4);
            workoutService.updateWorkout(optWorkout.get());
            p4.setWorkout(optWorkout.get());
            practiceService.createPractice(p4);

            Practice p5 = new Practice(exerciseService.findByName("Rudern vorg. 1-Arm").get(), 3);
            optWorkout.get().getPracticeList().add(p5);
            workoutService.updateWorkout(optWorkout.get());
            p5.setWorkout(optWorkout.get());
            practiceService.createPractice(p5);

            Practice p6 = new Practice(exerciseService.findByName("Rudern Seilzug").get(), 2);
            optWorkout.get().getPracticeList().add(p6);
            workoutService.updateWorkout(optWorkout.get());
            p6.setWorkout(optWorkout.get());
            practiceService.createPractice(p6);
        } else {
            throw new ResourceNotFoundException("initPracticeDate: Workout nicht gefunden");
        }

        optWorkout = workoutService.findByNameAndDatum("Bizepstraining", LocalDate.now());
        if (optWorkout.isPresent()) {

            Practice p7 = new Practice(exerciseService.findByName("Langhantel Bizeps-Curls").get(), 4);
            optWorkout.get().getPracticeList().add(p7);
            workoutService.updateWorkout(optWorkout.get());
            p7.setWorkout(optWorkout.get());
            practiceService.createPractice(p7);

            Practice p8 = new Practice(exerciseService.findByName("Scott-Curls K.H.").get(), 3);
            optWorkout.get().getPracticeList().add(p8);
            workoutService.updateWorkout(optWorkout.get());
            p8.setWorkout(optWorkout.get());
            practiceService.createPractice(p8);

            Practice p9 = new Practice(exerciseService.findByName("Hammer-Curls K.H.").get(), 2);
            optWorkout.get().getPracticeList().add(p9);
            workoutService.updateWorkout(optWorkout.get());
            p9.setWorkout(optWorkout.get());
            practiceService.createPractice(p9);

            Practice p10 = new Practice(exerciseService.findByName("Konzentrations-Curls K.H.").get(), 2);
            optWorkout.get().getPracticeList().add(p10);
            workoutService.updateWorkout(optWorkout.get());
            p10.setWorkout(optWorkout.get());
            practiceService.createPractice(p10);
        } else {
            throw new ResourceNotFoundException("initPracticeDate: Workout nicht gefunden");
        }

    }
}
