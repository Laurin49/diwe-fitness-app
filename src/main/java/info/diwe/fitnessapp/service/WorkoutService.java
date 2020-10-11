package info.diwe.fitnessapp.service;

import info.diwe.fitnessapp.model.Workout;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    List<Workout> readWorkouts();
    Workout readWorkout(Long id);
    Workout createWorkout(Workout workout);
    Workout updateWorkout(Workout workout);
    void deleteWorkout(Long id);

    Optional<Workout> findByNameAndDatum(String name, LocalDate datum);
    Optional<Workout> findFirstByDatum(LocalDate datum);
}
