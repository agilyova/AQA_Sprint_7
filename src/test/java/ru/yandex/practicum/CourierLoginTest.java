package ru.yandex.practicum;

import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import ru.yandex.practicum.objects.Courier;
import ru.yandex.practicum.steps.CourierSteps;

import static ru.yandex.practicum.helpers.DataGeneratorHelper.getLogin;
import static ru.yandex.practicum.helpers.DataGeneratorHelper.getPassword;

@DisplayName("Логин курьера")
public class CourierLoginTest extends BaseTest {
  static CourierSteps steps = new CourierSteps();
  static Courier existedCourier;
  static String login = getLogin();
  static String password = getPassword();

  String notExistedLogin = getLogin();
  String wrongPassword = getPassword();
  String emptyPassword = "";

  @BeforeClass
  public static void createCourier() {
    existedCourier = new Courier(login, password);
    steps.sendPostRequestCreateCourier(existedCourier);
  }

  @DisplayName("Авторизация курьера под валидными логином/паролем возвращает id курьера")
  @Test
  public void loginCourierCorrectCredentialsReturnCourierId() {
    Response response = steps.sendPostRequestLoginCourier(existedCourier);
    int existedCourierId = steps.getCourierId(response);

    steps.compareResponseCodeAndIdKey(response, 200, existedCourierId);
  }

  @DisplayName("Авторизация курьера под существующим логином и некорректным паролем возращает ошибку")
  @Test
  public void loginCourierWrongPasswordReturnError() {
    Courier courier = new Courier(login, wrongPassword);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Учетная запись не найдена");
  }

  @DisplayName("Авторизация курьера под несуществующим логином возращает ошибку")
  @Test
  public void loginCourierWrongLoginReturnError() {
    Courier courier = new Courier(notExistedLogin, password);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Учетная запись не найдена");
  }

  @Ignore("Долго висит")
  @Issue("Return 504")
  @DisplayName("Авторизация курьера без пароля возращает ошибку")
  @Test
  public void loginCourierWithOutPasswordReturnError() {
    Courier courier = new Courier();
    courier.setLogin(login);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для входа");
  }

  @DisplayName("Авторизация курьера c пустым паролем возращает ошибку")
  @Test
  public void loginCourierWithEmptyPasswordReturnError() {
    Courier courier = new Courier(login, emptyPassword);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для входа");
  }

  @AfterClass
  public static void deleteCourier() {
    steps.deleteCourier(existedCourier);
  }
}
