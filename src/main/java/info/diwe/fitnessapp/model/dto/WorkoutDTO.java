package info.diwe.fitnessapp.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WorkoutDTO {

    private Long id;

    @NotBlank(message="Workout Name muss eingetragen werden ...")
    @Size(min=3, max=50, message="Workout Name sollte mind. 3 Zeichen und max. 50 Zeichen haben ...")
    private String name;

    @NotNull(message="Workout Datum muss eingetragen werden ...")
    private String datum;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Long id, String name, String datum) {
        this.id = id;
        this.name = name;
        this.datum = datum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

}

