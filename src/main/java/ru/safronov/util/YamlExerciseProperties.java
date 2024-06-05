package ru.safronov.util;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.safronov.model.Exercise;

@Configuration
@ConfigurationProperties(prefix = "content")
@PropertySource(value = "exercises.yml", factory = YamlPropertySourceFactory.class)
public class YamlExerciseProperties {

  private List<Exercise> exercises;

  public List<Exercise> getExercises() {
    return exercises;
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = exercises;
  }
}
