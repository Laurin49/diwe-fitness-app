package info.diwe.fitnessapp.repository;

import info.diwe.fitnessapp.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long> {
}
