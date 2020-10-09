package info.diwe.fitnessapp.service.impl;

import info.diwe.fitnessapp.exception.ResourceNotFoundException;
import info.diwe.fitnessapp.model.Workout;
import info.diwe.fitnessapp.repository.WorkoutRepository;
import info.diwe.fitnessapp.service.WorkoutService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<Workout> readWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public Workout readWorkout(Long id) {
        Optional<Workout> result = workoutRepository.findById(id);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Workout not found with id: " + id);
        }
        return result.get();
    }

    @Override
    public Workout createWorkout(Workout workout) {
        Workout result = workoutRepository.save(workout);
        return result;
    }

    @Override
    public Workout updateWorkout(Workout workout) {
        Optional<Workout> optResult = workoutRepository.findById(workout.getId());
        if (!optResult.isPresent()) {
            throw new ResourceNotFoundException("Update Workout not found ...");
        }
        Workout result = workoutRepository.save(workout);
        return result;
    }

    @Override
    public void deleteWorkout(Long id) {
        Optional<Workout> result = workoutRepository.findById(id);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Workout not found ...");
        }
        workoutRepository.delete(result.get());
    }
}