package info.diwe.fitnessapp.service.impl;

import info.diwe.fitnessapp.exception.ResourceNotFoundException;
import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.enums.MuscleGroup;
import info.diwe.fitnessapp.repository.ExerciseRepository;
import info.diwe.fitnessapp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<Exercise> readExercises() {
        return (List<Exercise>) exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> readExercisesByMuscleGroup(String mgroup) {
        return exerciseRepository.findByMuscleGroupOrderByMuscleGroupAsc(MuscleGroup.valueOf(mgroup));
    }

    @Override
    public Exercise readExercise(Long id) {
        Optional<Exercise> result = exerciseRepository.findById(id);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Exercise-Resource mit Id: " + id + " not found");
        }
        return result.get();
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        Exercise result = exerciseRepository.save(exercise);
        return result;
    }

    @Override
    public Exercise updateExercise(Exercise exercise) {
        Exercise result = exerciseRepository.save(exercise);
        return result;
    }

    @Override
    public void deleteExercise(Long id) {
        Optional<Exercise> result = exerciseRepository.findById(id);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Exercise-Resource mit Id: " + id + " not found");
        }
        exerciseRepository.delete(result.get());
    }

    @Override
    public Exercise findByName(String name) {
        Optional<Exercise> result = exerciseRepository.findByName(name);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Exercise: " + name + " wurde nicht gefunden ...");
        }
        return result.get();
    }
}
