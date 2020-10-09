package info.diwe.fitnessapp.service;

import info.diwe.fitnessapp.model.Workout;

import java.util.List;

public interface WorkoutService {
    List<Workout> readWorkouts();
    Workout readWorkout(Long id);
    Workout createWorkout(Workout workout);
    Workout updateWorkout(Workout workout);
    void deleteWorkout(Long id);
}
