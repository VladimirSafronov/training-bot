package ru.safronov.util;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.safronov.model.Exercise;

@Component
public class ExerciseData {

  private List<Exercise> sortExercises;
  private final YamlExerciseProperties yamlExerciseProperties;

  @Autowired
  public ExerciseData(YamlExerciseProperties yamlExerciseProperties) {
    this.yamlExerciseProperties = yamlExerciseProperties;
  }

  public List<Exercise> getSortedExercises() {
    sortExercises();
    return sortExercises;
  }

  private List<Exercise> getExercises() {
    return yamlExerciseProperties.getExercises();
  }

  private void sortExercises() {
    sortExercises = getExercises();
    sortExercises.sort(Comparator.comparing(Exercise::getName));
  }
}
