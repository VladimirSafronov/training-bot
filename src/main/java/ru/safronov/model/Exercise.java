package ru.safronov.model;

public class Exercise {

  private final long id;
  private final String name;
  private final String url;

  public Exercise(long id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public long getId() {
    return id;
  }
}
