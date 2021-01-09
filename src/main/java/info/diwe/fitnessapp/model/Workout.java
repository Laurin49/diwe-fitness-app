package info.diwe.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.diwe.fitnessapp.model.enums.MuscleGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Workout Name muss eingetragen werden ...")
    @Size(min=3, max=50, message="Workout Name sollte mind. 3 Zeichen und max. 50 Zeichen haben ...")
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate datum;

    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Practice> practiceList = new ArrayList<>();

    public Workout() {}

    public Workout(@NotBlank(message = "Workout Name muss eingetragen werden")
                   @Size(min = 3, max = 50, message = "Workout Name sollte mind. 3 Zeichen und max. 50 Zeichen haben ...")
                           String name,
                   @NotNull LocalDate datum) {
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public List<Practice> getPracticeList() {
        return practiceList;
    }

    public void setPracticeList(List<Practice> practiceList) {
        this.practiceList = practiceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
