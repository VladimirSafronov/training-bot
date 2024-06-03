package ru.safronov.util;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.safronov.model.Exercise;

@Component
public class ExerciseData {

  private static long idCounter = 1;
  private List<Exercise> sortExercises;
  private YamlExerciseProperties yamlExerciseProperties;

  @Autowired
  public ExerciseData(YamlExerciseProperties yamlExerciseProperties) {
    this.yamlExerciseProperties = yamlExerciseProperties;
  }

  public List<Exercise> getSortedExercises() {
    sortExercises();
    return sortExercises;
  }

  private List<Exercise> getExercises() {
    List<Exercise> exercises = new ArrayList<>();
    addExercisesToList(exercises, yamlExerciseProperties.getNames(), yamlExerciseProperties.getUrls());
    return exercises;
  }

  private void sortExercises() {
    sortExercises = getExercises();
    sortExercises.sort(Comparator.comparing(Exercise::getName));
  }

  private void addExercisesToList(List<Exercise> finalList, List<String> exerciseNames,
      List<String> exerciseUrls) {
    for (int i = 0; i < exerciseNames.size(); i++) {
      finalList.add(new Exercise(idCounter++, exerciseNames.get(i), exerciseUrls.get(i)));
    }
  }
}
