package info.diwe.fitnessapp.repository;

import info.diwe.fitnessapp.model.Workout;
import org.hibernate.jdbc.Work;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAll(Sort sort);
    Optional<Workout> findByNameAndAndDatum(String name, LocalDate datum);
    Optional<Workout> findFirstByDatum(LocalDate datum);
}
