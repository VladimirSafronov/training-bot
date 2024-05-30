package ru.safronov.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ru.safronov.model.Exercise;

public class ExerciseData {

  private static long idCounter = 1;
  private static List<Exercise> sortExercises;

  public static List<Exercise> getSortExercises() {
    sortExercises();
    return sortExercises;
  }

  private static List<Exercise> getExercises() {
    List<Exercise> exercises = new ArrayList<>();
    exercises.add(new Exercise(idCounter++, "с_ноги_на_ногу", "url1"));
    exercises.add(new Exercise(idCounter++, "высокое_бедро", "url2"));
    exercises.add(new Exercise(idCounter++, "захлест", "url3"));
    exercises.add(new Exercise(idCounter++, "олений_бег", "url4"));
    exercises.add(new Exercise(idCounter++, "школьница", "url5"));
    exercises.add(new Exercise(idCounter++, "семенящий_бег", "url6"));

    return exercises;
  }

  private static void sortExercises() {
    sortExercises = getExercises();
    sortExercises.sort(Comparator.comparing(Exercise::getName));
  }
}
