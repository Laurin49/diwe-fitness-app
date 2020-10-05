package info.diwe.fitnessapp.service;

import info.diwe.fitnessapp.model.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> readExercises();
    List<Exercise> readExercisesByMuscleGroup(String mgroup);

    Exercise readExercise(Long id);
    Exercise createExercise(Exercise exercise);
    Exercise updateExercise(Exercise exercise);
    void deleteExercise(Long id);

    Exercise findByName(String name);
}
