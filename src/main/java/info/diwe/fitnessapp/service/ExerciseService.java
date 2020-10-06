package info.diwe.fitnessapp.service;

import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.enums.MuscleGroup;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    List<Exercise> readExercises();
    List<Exercise> readExercisesByMuscleGroup(String mgroup);

    Exercise readExercise(Long id);
    Exercise createExercise(Exercise exercise);
    Exercise updateExercise(Exercise exercise);
    void deleteExercise(Long id);

    Optional<Exercise> findByName(String name);
    Optional<Exercise> findByNameAndMuscleGroup(String name, MuscleGroup muscleGroup);
}
