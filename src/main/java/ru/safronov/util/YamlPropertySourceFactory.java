package ru.safronov.util;

import java.util.Objects;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * This class provides custom implementation of the PropertySourceFactory, which will handle the
 * YAML file processing
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

  @NotNull
  @Override
  public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(resource.getResource());

    Properties properties = factory.getObject();

    return new PropertiesPropertySource(
        Objects.requireNonNull(resource.getResource().getFilename()),
        Objects.requireNonNull(properties));
  }
}
