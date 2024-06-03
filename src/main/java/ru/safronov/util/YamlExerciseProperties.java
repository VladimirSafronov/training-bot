package ru.safronov.util;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "exercise")
@PropertySource(value = "exercises.yml", factory = YamlPropertySourceFactory.class)
public class YamlExerciseProperties {

  private List<String> names;
  private List<String> urls;

  public List<String> getNames() {
    return names;
  }

  public void setNames(List<String> names) {
    this.names = names;
  }

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }
}
