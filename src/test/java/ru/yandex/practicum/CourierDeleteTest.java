package ru.yandex.practicum;

import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.objects.Courier;
import ru.yandex.practicum.steps.CourierSteps;

@DisplayName("Удаление курьера")
public class CourierDeleteTest extends BaseTest {

  CourierSteps steps = new CourierSteps();

  @DisplayName("Удаление существующего курьера удаляет курьера")
  @Test
  public void deleteCourierExistedIdDeletesCourier() {
    Courier courier = new Courier("a_gilyova", "123456");

    int courierId = steps.createCourierAndGetItId(courier);

    Response deleteResponse = steps.sendDeleteRequestDeleteCourier(courierId);
    steps.compareResponseCodeAndOkValue(deleteResponse, 200, true);
  }

  @DisplayName("Удаление несуществующего курьера возвращает ошибку")
  @Test
  public void deleteCourierWrongIdReturnsError() {
    Response response = steps.sendDeleteRequestDeleteCourier(-1);

    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Курьера с таким id нет.");
  }

  @Issue("Return 404")
  @DisplayName("Удаление курьера без id возвращает ошибку")
  @Test
  public void deleteCourierEmptyIdReturnsError() {
    Response response = steps.sendDeleteRequestDeleteCourier();

    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для удаления курьера");
  }
}
