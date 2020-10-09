package info.diwe.fitnessapp.controller;

import info.diwe.fitnessapp.model.Workout;
import info.diwe.fitnessapp.model.dto.WorkoutDTO;
import info.diwe.fitnessapp.service.WorkoutService;
import info.diwe.fitnessapp.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/workouts")
public class WorkoutController {

    private WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    private List<Workout> workouts = new ArrayList<>();

    @PostConstruct
    public void init() {

    }

    @GetMapping("/list")
    public ModelAndView getWorkoutList(ModelAndView modelAndView) {
        workouts = workoutService.readWorkouts();

        modelAndView.addObject("workouts", workouts);
        modelAndView.setViewName("workouts/list");
        return modelAndView;
    }

    // Add Workout

    @GetMapping("/add")
    public ModelAndView addWorkoutGet(ModelAndView modelAndView) {

        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setDatum(DateUtil.getLocalDateAsString());

        modelAndView.addObject("workout", workoutDTO);
        modelAndView.setViewName("workouts/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addWorkoutPost(@ModelAttribute("workout") @Valid WorkoutDTO workoutDTO,
                                       BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView = showMsg("add", workoutDTO, "Fehler beim Speichern des Workouts ...", false,
                    modelAndView);
            return modelAndView;
        }

        Workout workout = new Workout();
        if (workoutDTO.getId() != null) {
            workout.setId(workoutDTO.getId());
        }
        workout.setName(workoutDTO.getName());
        workout.setDatum(LocalDate.parse(workoutDTO.getDatum()));

        workoutService.createWorkout(workout);

        modelAndView = showMsg("add", workoutDTO, "Workout hinzugefügt ...", true, modelAndView);
        return modelAndView;
    }


    // Update Workout
    @GetMapping("/update/{id}")
    public ModelAndView updateWorkoutGet(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Workout workout = workoutService.readWorkout(id);
        WorkoutDTO workoutDTO = new WorkoutDTO(workout.getId(), workout.getName(), workout.getDatum().toString());

        modelAndView.addObject("workout", workoutDTO);
        modelAndView.setViewName("workouts/update");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateWorkoutPost(@ModelAttribute("workout") @Valid WorkoutDTO workoutDTO,
                                          BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            showMsg("update", workoutDTO, "Fehler beim Update des Workouts ...", false, modelAndView);
            return modelAndView;
        }

        Workout workout = new Workout();
        if (workoutDTO.getId() != null) {
            workout.setId(workoutDTO.getId());
        }
        workout.setName(workoutDTO.getName());
        workout.setDatum(LocalDate.parse(workoutDTO.getDatum()));

        workoutService.updateWorkout(workout);

        showMsg("update", workoutDTO, "Update Workout erfolgreich !!! ", true, modelAndView);

        return modelAndView;
    }

    private ModelAndView showMsg(String type, WorkoutDTO workoutDTO, String msg, boolean is_success,
                                 ModelAndView modelAndView) {
        modelAndView.addObject("message", msg);

        if (is_success) {
            modelAndView.addObject("alertClass", "alert-success");
        } else {
            modelAndView.addObject("alertClass", "alert-danger");
        }
        modelAndView.addObject("workout", workoutDTO);

        modelAndView.setViewName("workouts/" + type);
        return modelAndView;
    }

    // Delete Workout
    @GetMapping("/delete/{id}")
    public ModelAndView deleteWorkout(@PathVariable("id") Long id, ModelAndView modelAndView) {
        workoutService.deleteWorkout(id);

        getWorkoutList(modelAndView);
        return modelAndView;
    }
}
