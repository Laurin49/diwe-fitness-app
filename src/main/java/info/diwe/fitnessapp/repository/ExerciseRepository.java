package info.diwe.fitnessapp.repository;

import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.enums.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroupOrderByMuscleGroupAsc(MuscleGroup muscleGroup);
    Optional<Exercise> findByName(String name);
}
