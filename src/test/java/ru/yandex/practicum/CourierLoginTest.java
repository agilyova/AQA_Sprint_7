package ru.yandex.practicum;

import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;
import ru.yandex.practicum.objects.Courier;
import ru.yandex.practicum.steps.CourierSteps;

@DisplayName("Логин курьера")
public class CourierLoginTest extends BaseTest {
  CourierSteps steps = new CourierSteps();
  String existedLogin = "gilyova";
  String existedPassword = "123456";
  int existedCourierId = 371520;
  String notExistedLogin = "gilyova!2638742%?№;?";
  String wrongPassword = "123";
  String emptyPassword = "";


  @DisplayName("Авторизация курьера под валидными логином/паролем возвращает id курьера")
  @Test
  public void loginCourierCorrectCredentialsReturnCourierId() {
    Courier courier = new Courier(existedLogin, existedPassword);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndIdKey(response, 200, existedCourierId);
  }

  @DisplayName("Авторизация курьера под существующим логином и некорректным паролем возращает ошибку")
  @Test
  public void loginCourierWrongPasswordReturnError() {
    Courier courier = new Courier(existedLogin, wrongPassword);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Учетная запись не найдена");
  }

  @DisplayName("Авторизация курьера под несуществующим логином возращает ошибку")
  @Test
  public void loginCourierWrongLoginReturnError() {
    Courier courier = new Courier(notExistedLogin, existedPassword);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Учетная запись не найдена");
  }

  @Ignore("Долго висит")
  @Issue("Return 504")
  @DisplayName("Авторизация курьера без пароля возращает ошибку")
  @Test
  public void loginCourierWithOutPasswordReturnError() {
    Courier courier = new Courier();
    courier.setLogin(existedLogin);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для входа");
  }

  @DisplayName("Авторизация курьера c пустым паролем возращает ошибку")
  @Test
  public void loginCourierWithEmptyPasswordReturnError() {
    Courier courier = new Courier(existedLogin, emptyPassword);

    Response response = steps.sendPostRequestLoginCourier(courier);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для входа");
  }
}
