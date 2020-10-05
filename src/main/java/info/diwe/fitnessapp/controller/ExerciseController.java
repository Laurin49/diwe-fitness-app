package info.diwe.fitnessapp.controller;

import info.diwe.fitnessapp.command.FilterMuscleGroup;
import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.enums.MuscleGroup;
import info.diwe.fitnessapp.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    private List<String> muscleGroups = new ArrayList<>();
    private FilterMuscleGroup filterMuscleGroup;
    private List<Exercise> exercises = new ArrayList<>();

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostConstruct
    public void init() {
        muscleGroups.add("Alle");
        for (MuscleGroup muscleGroup: MuscleGroup.values()) {
            muscleGroups.add(muscleGroup.name());
        }
        filterMuscleGroup = new FilterMuscleGroup();
        filterMuscleGroup.setFiltermuscle("Alle");
    }

    @GetMapping("/list")
    public ModelAndView getExerciseList(ModelAndView modelAndView) {

        exercises = holeExercises(filterMuscleGroup.getFiltermuscle());

        muscleGroupContainsAlle();

        modelAndView.addObject("muscleGroups", muscleGroups);
        modelAndView.addObject("filterMuscleGroup", filterMuscleGroup);

        modelAndView.addObject("exercises", exercises);
        modelAndView.setViewName("exercises/list");
        return modelAndView;
    }

    @PostMapping("/filter")
    public ModelAndView getExerciseFilterList(@ModelAttribute FilterMuscleGroup fMuscleGroup, ModelAndView modelAndView) {

        filterMuscleGroup.setFiltermuscle(fMuscleGroup.getFiltermuscle());

        exercises = holeExercises(fMuscleGroup.getFiltermuscle());

        muscleGroupContainsAlle();

        modelAndView.addObject("muscleGroups", muscleGroups);
        modelAndView.addObject("filterMuscleGroup", filterMuscleGroup);

        modelAndView.addObject("exercises", exercises);
        modelAndView.setViewName("exercises/list");
        return modelAndView;
    }

    private void muscleGroupContainsAlle() {
        if (!muscleGroups.contains("Alle")) {
            muscleGroups.add(0, "Alle");
        }
    }

    @GetMapping("/add")
    public ModelAndView addExerciseGet(ModelAndView modelAndView) {
        Exercise exercise = new Exercise();
        if (!filterMuscleGroup.getFiltermuscle().equals("Alle")) {
            exercise.setMuscleGroup(MuscleGroup.valueOf(filterMuscleGroup.getFiltermuscle()));
        }
        if (muscleGroups.contains("Alle")) {
            muscleGroups.remove("Alle");
        }

        modelAndView.addObject("filterMuscleGroup", filterMuscleGroup);
        modelAndView.addObject("muscleGroups", muscleGroups);

        modelAndView.addObject("exercise", exercise);
        modelAndView.setViewName("exercises/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addExercisePost(@ModelAttribute @Valid Exercise exercise, BindingResult bindingResult,
             ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Übungen - Fehler beim speichern ...");
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("exercise", exercise);
            modelAndView.setViewName("exercises/add");
            return modelAndView;
        }
        exerciseService.createExercise(exercise);

        modelAndView.addObject("message", "Übung hinzugefügt");
        modelAndView.addObject("alertClass", "alert-success");

        modelAndView.addObject("muscleGroups", muscleGroups);

        modelAndView.addObject("exercise", exercise);
        modelAndView.setViewName("exercises/add");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateExerciseGet(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Exercise exercise = exerciseService.readExercise(id);
        if (muscleGroups.contains("Alle")) {
            muscleGroups.remove("Alle");
        }
        modelAndView.addObject("filterMuscleGroup", filterMuscleGroup);
        modelAndView.addObject("muscleGroups", muscleGroups);

        modelAndView.addObject("exercise", exercise);
        modelAndView.setViewName("exercises/update");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateExercisePost(@ModelAttribute @Valid Exercise exercise, BindingResult bindingResult,
                                        ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Übungen - Fehler beim speichern ...");
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("exercise", exercise);
            modelAndView.setViewName("exercises/update");
            return modelAndView;
        }
        exerciseService.createExercise(exercise);

        modelAndView.addObject("message", "Übung geändert");
        modelAndView.addObject("alertClass", "alert-success");
        modelAndView.addObject("muscleGroups", muscleGroups);

        modelAndView.addObject("exercise", exercise);
        modelAndView.setViewName("exercises/update");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteExerciseGet(@PathVariable("id") Long id, ModelAndView modelAndView) {

        exerciseService.deleteExercise(id);

        exercises = holeExercises(filterMuscleGroup.getFiltermuscle());

        muscleGroupContainsAlle();

        modelAndView.addObject("muscleGroups", muscleGroups);
        modelAndView.addObject("filterMuscleGroup", filterMuscleGroup);

        modelAndView.addObject("exercises", exercises);
        modelAndView.setViewName("exercises/list");
        return modelAndView;
    }


    private List<Exercise> holeExercises(String muskelGruppe) {
        if (muskelGruppe.equals("Alle")) {
            return exerciseService.readExercises();
        } else {
            return exerciseService.readExercisesByMuscleGroup(muskelGruppe);
        }
    }
}
