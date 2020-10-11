package info.diwe.fitnessapp.service;

import info.diwe.fitnessapp.model.Practice;

import java.util.List;
import java.util.Optional;

public interface PracticeService {

    List<Practice> readPractices();
    Optional<Practice> readPractice(Long id);
    Practice createPractice(Practice practice);
    Practice updatePractice(Practice practice);
    void deletePractice(Long id);

}
