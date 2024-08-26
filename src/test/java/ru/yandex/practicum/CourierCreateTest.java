package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.objects.Courier;
import ru.yandex.practicum.steps.CourierSteps;

import java.util.Random;

@DisplayName("Создание курьера")
public class CourierCreateTest extends BaseTest {
  CourierSteps steps = new CourierSteps();
  String existedLogin = "gilyova_";
  String notExistedLogin = "gilyovaav";

  @DisplayName("Создание курьера с полным набором корректных данных создает курьера")
  @Test
  public void createCourierCorrectAllFieldsCreatesCourier() {
    String login = "gilyova" + new Random().nextInt();
    Courier courier = new Courier(login, "123456", "Александра");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndOkValue(response, 201, true);

    steps.deleteCourier(courier);
  }

  @DisplayName("Создание курьера только с обязательными полями создает курьера")
  @Test
  public void createCourierOnlyLoginAndPasswordCreatesCourier() {
    String login = "gilyova" + new Random().nextInt();
    Courier courier = new Courier();
    courier.setLogin(login);
    courier.setPassword("123456");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndOkValue(response, 201, true);

    steps.deleteCourier(courier);
  }

  @DisplayName("Создание курьера с уже существующим логином возвращает ошибку")
  @Test
  public void createCourierExistedDataReturnConflictError() {
    Courier courier = new Courier(existedLogin, "123", "Александра");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 409, "Этот логин уже используется. Попробуйте другой.");
  }

  @DisplayName("Создание курьера без обязательного поля Пароль возвращает ошибку")
  @Test
  public void createCourierOnlyLoginReturnNotEnoughDataError() {
    Courier courier = new Courier();
    courier.setLogin(notExistedLogin);

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }

  @DisplayName("Создание курьера без обязательного поля Логин возвращает ошибку")
  @Test
  public void createCourierOnlyPasswordReturnNotEnoughDataError() {
    Courier courier = new Courier();
    courier.setPassword("123456");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }

  @DisplayName("Создание курьера, когда Логин - пустая строка, возвращает ошибку")
  @Test
  public void createCourierOnlyPasswordAndEmptyLoginReturnNotEnoughDataError() {
    Courier courier = new Courier();
    courier.setLogin("");
    courier.setPassword("123456");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }

  @DisplayName("Создание курьера, когда Пароль пустая строка, возвращает ошибку")
  @Test
  public void createCourierOnlyLoginAndEmptyPasswordReturnNotEnoughDataError() {
    Courier courier = new Courier();
    courier.setLogin(notExistedLogin);
    courier.setPassword("");

    Response response = steps.sendPostRequestCreateCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для создания учетной записи");
  }
}
