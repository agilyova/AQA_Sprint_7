package ru.yandex.practicum.helpers;

import com.github.javafaker.Faker;

public class DataGeneratorHelper {
  private static Faker faker = new Faker();

  public static String getLogin() {
    return faker.name().lastName() + faker.number().randomNumber();
  }

  public static String getPassword() {
    return faker.lorem().characters(6, 10, true);
  }

  public static String getName() {
    return faker.name().firstName();
  }
}
