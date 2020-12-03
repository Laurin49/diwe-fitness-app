package info.diwe.fitnessapp.controller;

import info.diwe.fitnessapp.command.FilterExercise;
import info.diwe.fitnessapp.model.Exercise;
import info.diwe.fitnessapp.model.Practice;
import info.diwe.fitnessapp.model.Workout;
import info.diwe.fitnessapp.model.dto.WorkoutDTO;
import info.diwe.fitnessapp.service.ExerciseService;
import info.diwe.fitnessapp.service.PracticeService;
import info.diwe.fitnessapp.service.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/practices")
public class PracticeController {

    private PracticeService practiceService;
    private WorkoutService workoutService;
    private ExerciseService exerciseService;

    private List<Practice> practices;
    private List<Workout> workouts;
    private List<Exercise> exercises;
    private List<String> exerciseNames;

    private Workout currWorkout;
    private Optional<Exercise> currExercise;
    private Boolean isAddPractice = false;

    public PracticeController(PracticeService practiceService, WorkoutService workoutService,
                              ExerciseService exerciseService) {
        this.practiceService = practiceService;
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
    }

    @PostConstruct
    public void init() {
        practices = new ArrayList<>();
        workouts = new ArrayList<>();
        exercises = new ArrayList<>();
        exerciseNames = new ArrayList<>();
        currWorkout = new Workout();
        currWorkout.setName("init");
    }

    private void setExercisesAndNames() {
        exercises = exerciseService.readExercises();
        exerciseNames.clear();
        for (Exercise ex : exercises) {
            exerciseNames.add(ex.getName());
        }
    }

    @GetMapping("/list")
    public ModelAndView practicesList(ModelAndView modelAndView) {
        workouts = workoutService.readWorkouts();

        if (currWorkout.getName().equals("init")) {
            isAddPractice = false;
        } else {
            isAddPractice = true;
        }
        practices = currWorkout.getPracticeList();

        modelAndView.addObject("isAddPractice", isAddPractice);

        modelAndView.addObject("currWorkout", currWorkout);
        modelAndView.addObject("workouts", workouts);
        modelAndView.addObject("practices", practices);
        modelAndView.setViewName("practices/list");
        return modelAndView;
    }

    @GetMapping("/selectByWorkout/{id}")
    public ModelAndView practicesByWorkout(@PathVariable("id") Long id, ModelAndView modelAndView) {

        currWorkout = workoutService.readWorkout(id);
        for (Practice practice: currWorkout.getPracticeList()) {
            System.out.println("Practice: " + practice.getExercise().getName() + " - Sätze: " + practice.getCount());
        }
        practices = currWorkout.getPracticeList();
        isAddPractice = true;
        modelAndView.addObject("isAddPractice", isAddPractice);
        modelAndView.addObject("currWorkout", currWorkout);
        modelAndView.addObject("workouts", workouts);
        modelAndView.addObject("practices", practices);
        modelAndView.setViewName("practices/list");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addPracticeGet(ModelAndView modelAndView) {
        Practice practice = new Practice();

        setExercisesAndNames();

        modelAndView.addObject("practice", practice);
        modelAndView.addObject("exerciseNames", exerciseNames);
        modelAndView.setViewName("practices/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addPracticePost(@ModelAttribute("practice") @Valid Practice practice,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        exercises = exerciseService.readExercises();
        if (bindingResult.hasErrors()) {
            modelAndView = showMsg("add", practice, exerciseNames, "Fehler beim Speichern des Practice ...",
                    false, modelAndView);
            return modelAndView;
        }

        currExercise = exerciseService.findByName(practice.getExercise().getName());
        if (!currExercise.isPresent()) {
            modelAndView = showMsg("add", practice, exerciseNames, "Fehler Exercise nicht gefunden ...",
                    false, modelAndView);
            return modelAndView;
        }

        practice.setWorkout(currWorkout);
        practice.setExercise(currExercise.get());
        practiceService.createPractice(practice);

        currWorkout.getPracticeList().add(practice);
        workoutService.updateWorkout(currWorkout);

        setExercisesAndNames();

        modelAndView = showMsg("add", practice, exerciseNames, "Hinzufügen Practice erfolgreich ...",
                true, modelAndView);

        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updatePracticeGet(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Optional<Practice> practice = practiceService.readPractice(id);

        setExercisesAndNames();

        modelAndView.addObject("currWorkout", currWorkout);
        modelAndView.addObject("practice", practice.get());
        modelAndView.addObject("exerciseNames", exerciseNames);
        modelAndView.setViewName("practices/update");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updatePracticePost(@ModelAttribute("practice") @Valid Practice practice,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            showMsg("update", practice, exerciseNames, "Fehler Update Practice ...", false, modelAndView);
            return modelAndView;
        }

        System.out.println("Practice: " + practice);
        System.out.println("Practice Exercise: " + practice.getExercise());
        System.out.println("Practice Count: " + practice.getCount());

        currExercise = exerciseService.findByName(practice.getExercise().getName());
        if (!currExercise.isPresent()) {
            modelAndView = showMsg("update", practice, exerciseNames, "Fehler Exercise nicht gefunden ...",
                    false, modelAndView);
            return modelAndView;
        }

        practice.setExercise(currExercise.get());
        practice.setWorkout(currWorkout);

        practiceService.updatePractice(practice);

        setExercisesAndNames();

        showMsg("update", practice, exerciseNames, "Update Practice erfolgreich !!!", true, modelAndView);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deletePractice(@PathVariable("id") Long id , ModelAndView modelAndView) {

        Optional<Practice> delPractice = practiceService.readPractice(id);
        currWorkout.getPracticeList().remove(delPractice.get());
        workoutService.updateWorkout(currWorkout);

        practiceService.deletePractice(id);

        modelAndView.setViewName("redirect:/practices/selectByWorkout/" + currWorkout.getId());
        return modelAndView;
    }

    private ModelAndView showMsg(String type, Practice practice, List<String> exerciseNames, String msg,
                                 boolean is_success,
                                 ModelAndView modelAndView) {
        modelAndView.addObject("message", msg);

        if (is_success) {
            modelAndView.addObject("alertClass", "alert-success");
        } else {
            modelAndView.addObject("alertClass", "alert-danger");
        }
        modelAndView.addObject("currWorkout", currWorkout);
        modelAndView.addObject("exerciseNames", exerciseNames);
        modelAndView.addObject("practice", practice);

        modelAndView.setViewName("practices/" + type);
        return modelAndView;
    }

    @GetMapping("/copyWorkout/{id}")
    public ModelAndView practicesCopy(@PathVariable("id") Long id, ModelAndView modelAndView) {
        currWorkout = workoutService.readWorkout(id);
        workoutService.copyWorkout(id);

        practices = currWorkout.getPracticeList();
        workouts = workoutService.readWorkouts();

        modelAndView.addObject("currWorkout", currWorkout);
        modelAndView.addObject("workouts", workouts);
        modelAndView.addObject("practices", practices);
        modelAndView.setViewName("practices/list");
        return modelAndView;
    }


}
