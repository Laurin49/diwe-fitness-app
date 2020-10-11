package info.diwe.fitnessapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "practices")
public class Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int count;

    @ManyToOne()
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public Practice() {
    }

    public Practice(Exercise exercise, int count) {
        this.exercise = exercise;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Practice practice = (Practice) o;
        return Objects.equals(id, practice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Practice{" +
                "id=" + id +
                ", exercise=" + exercise.getName() +
                '}';
    }
}
