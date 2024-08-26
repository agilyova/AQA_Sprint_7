package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class BaseSteps {

  @Step("Сравнение статус кода и значения ключа \"ok\" с ожидаемыми значениями")
  public void compareResponseCodeAndOkValue(Response response, int expectedStatusCode, Boolean expectedValue) {
    response.
      then().
      statusCode(expectedStatusCode).
      body("ok", equalTo(expectedValue));
  }

  @Step("Сравнение статус кода и значения ключа \"id\" с ожидаемыми значениями")
  public void compareResponseCodeAndIdKey(Response response, int expectedStatusCode, int expectedIdValue) {
    response.
      then().
      statusCode(expectedStatusCode).
      body("id", equalTo(expectedIdValue));
  }

  @Step("Сравнение статус кода и поля message из ответа с ожидаемыми значениями")
  public void compareResponseCodeAndMessageToExpectedValues(Response response, int expectedStatusCode, String expectedText) {
    response.
      then().
      statusCode(expectedStatusCode).
      body("message", equalTo(expectedText));
  }
}
