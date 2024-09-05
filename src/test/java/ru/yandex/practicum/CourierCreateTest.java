package ru.yandex.practicum;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.objects.Courier;
import ru.yandex.practicum.steps.CourierSteps;

import static ru.yandex.practicum.helpers.DataGeneratorHelper.*;

@DisplayName("Создание курьера")
public class CourierCreateTest extends BaseTest {
  Faker faker = new Faker();
  CourierSteps steps = new CourierSteps();
  String login;
  String password;
  String name;

  @DisplayName("Создание курьера с полным набором корректных данных создает курьера")
  @Test
  public void createCourierCorrectAllFieldsCreatesCourier() {
    login = getLogin();
    password = getPassword();
    name = getName();
    Courier courier = new Courier(login, password, name);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndOkValue(response, 201, true);

    steps.deleteCourier(courier);
  }

  @DisplayName("Создание курьера только с обязательными полями создает курьера")
  @Test
  public void createCourierOnlyLoginAndPasswordCreatesCourier() {
    login = getLogin();
    password = getPassword();
    Courier courier = new Courier(login, password);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndOkValue(response, 201, true);

    steps.deleteCourier(courier);
  }

  @DisplayName("Создание курьера с уже существующим логином возвращает ошибку")
  @Test
  public void createCourierExistedDataReturnConflictError() {
    login = getLogin();
    password = getPassword();
    Courier courier = new Courier(login, password);
    steps.sendPostRequestCreateCourier(courier);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 409, "Этот логин уже используется. Попробуйте другой.");

    steps.deleteCourier(courier);
  }

  @DisplayName("Создание курьера без обязательного поля Пароль возвращает ошибку")
  @Test
  public void createCourierOnlyLoginReturnNotEnoughDataError() {
    login = getLogin();
    Courier courier = new Courier();
    courier.setLogin(login);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }

  @DisplayName("Создание курьера без обязательного поля Логин возвращает ошибку")
  @Test
  public void createCourierOnlyPasswordReturnNotEnoughDataError() {
    password = getPassword();
    Courier courier = new Courier();
    courier.setPassword(password);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }

  @DisplayName("Создание курьера, когда Логин - пустая строка, возвращает ошибку")
  @Test
  public void createCourierOnlyPasswordAndEmptyLoginReturnNotEnoughDataError() {
    password = getPassword();
    Courier courier = new Courier();
    courier.setLogin("");
    courier.setPassword(password);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }

  @DisplayName("Создание курьера, когда Пароль пустая строка, возвращает ошибку")
  @Test
  public void createCourierOnlyLoginAndEmptyPasswordReturnNotEnoughDataError() {
    login = getLogin();
    Courier courier = new Courier();
    courier.setLogin(login);
    courier.setPassword("");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }
}
