package info.diwe.fitnessapp.repository;

import info.diwe.fitnessapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

//    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);
    Optional<Exercise> findByName(String name);
}
