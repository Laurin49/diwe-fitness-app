package info.diwe.fitnessapp.controller;

import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.repository.ExerciseRepository;
import info.diwe.fitnessapp.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/list")
    public ModelAndView getExerciseList(ModelAndView modelAndView) {

        List<Exercise> exercises = exerciseService.readExercises();

        modelAndView.addObject("exercises", exercises);
        modelAndView.setViewName("exercises/list");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addExerciseGet(ModelAndView modelAndView) {
        Exercise exercise = new Exercise();

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

        modelAndView.addObject("exercise", exercise);
        modelAndView.setViewName("exercises/add");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateExerciseGet(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Exercise exercise = exerciseService.readExercise(id);

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

        modelAndView.addObject("exercise", exercise);
        modelAndView.setViewName("exercises/update");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteExerciseGet(@PathVariable("id") Long id, ModelAndView modelAndView) {

        exerciseService.deleteExercise(id);

        List<Exercise> exercises = exerciseService.readExercises();

        modelAndView.addObject("exercises", exercises);
        modelAndView.setViewName("exercises/list");
        return modelAndView;
    }
}
