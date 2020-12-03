package info.diwe.fitnessapp.service.impl;

import info.diwe.fitnessapp.exception.ResourceNotFoundException;
import info.diwe.fitnessapp.model.Practice;
import info.diwe.fitnessapp.model.Workout;
import info.diwe.fitnessapp.repository.PracticeRepository;
import info.diwe.fitnessapp.repository.WorkoutRepository;
import info.diwe.fitnessapp.service.WorkoutService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private WorkoutRepository workoutRepository;
    private PracticeRepository practiceRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, PracticeRepository practiceRepository) {
        this.workoutRepository = workoutRepository;
        this.practiceRepository = practiceRepository;
    }

    @Override
    public List<Workout> readWorkouts() {
        return workoutRepository.findAll(new Sort(Sort.Direction.DESC, "datum"));
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

    @Override
    public Optional<Workout> findByNameAndDatum(String name, LocalDate datum) {
        Optional<Workout> result = workoutRepository.findByNameAndAndDatum(name, datum);
        return result;
    }

    @Override
    public Optional<Workout> findFirstByDatum(LocalDate datum) {
        Optional<Workout> result = workoutRepository.findFirstByDatum(datum);
        return result;
    }

    @Override
    public Workout copyWorkout(Long id) {
        Optional<Workout> copyWorkout = workoutRepository.findById(id);
        if (!copyWorkout.isPresent()) {
            throw new ResourceNotFoundException("Workout not found ...");
        }
        Workout newWorkout = new Workout();
        newWorkout.setName(copyWorkout.get().getName());
        newWorkout.setDatum(LocalDate.now());

        final Workout result = workoutRepository.save(newWorkout);

        List<Practice> practiceList = copyWorkout.get().getPracticeList();

        practiceList.stream().forEach(practice -> {
            Practice pr = new Practice();
            pr.setExercise(practice.getExercise());
            pr.setCount(practice.getCount());
            pr.setWorkout(result);
            practiceRepository.save(pr);
            result.getPracticeList().add(pr);
        });

        Workout resultWorkout = workoutRepository.save(result);
        return resultWorkout;
    }
}
