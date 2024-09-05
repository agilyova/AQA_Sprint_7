package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.practicum.objects.Courier;
import ru.yandex.practicum.objects.Order;
import ru.yandex.practicum.steps.CourierSteps;
import ru.yandex.practicum.steps.OrderSteps;

import static ru.yandex.practicum.helpers.DataGeneratorHelper.getLogin;
import static ru.yandex.practicum.helpers.DataGeneratorHelper.getPassword;

@DisplayName("Принятие заказа")
public class OrderAcceptTest extends BaseTest {

  static OrderSteps steps = new OrderSteps();
  static CourierSteps courierSteps = new CourierSteps();
  static int existedCourierId;

  @BeforeClass
  public static void arrange() {
    Courier courier = new Courier(getLogin(), getPassword());
    courierSteps.sendPostRequestCreateCourier(courier);
    Response response = courierSteps.sendPostRequestLoginCourier(courier);
    existedCourierId = courierSteps.getCourierId(response);
  }

  @DisplayName("Принятие заказа с существующим id заказа и id курьера проходит успешно")
  @Test
  public void orderAcceptValidDataAcceptOrder() {
    Order order = new Order();
    int orderId = steps.orderCreateAndGetOrderIdAndTrackNumber(order).get("id");

    Response response = steps.sendPutRequestAcceptOrder(orderId, existedCourierId);

    steps.compareResponseCodeAndOkValue(response, 200, true);
  }

  @DisplayName("Принятие заказа без указания id курьера возращает ошибку")
  @Test
  public void orderAcceptWithOutCourierIdReturnError() {
    Order order = new Order();
    int orderId = steps.orderCreateAndGetOrderIdAndTrackNumber(order).get("id");

    Response response = steps.sendPutRequestAcceptOrder(orderId);
    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для поиска");
  }

  @DisplayName("Принятие заказа без указания id заказа возращает ошибку")
  @Test
  public void orderAcceptWithOutOrderIdReturnError() {
    Response response = steps.sendPutRequestAcceptOrder(existedCourierId);

    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для поиска");
  }

  @DisplayName("Принятие заказа по уже принятому заказу возращает ошибку")
  @Test
  public void orderAcceptAlreadyAcceptedOrderIdReturnError() {
    Order order = new Order();
    int alreadyAcceptedOrderId = steps.orderCreateAndGetOrderIdAndTrackNumber(order).get("id");
    steps.sendPutRequestAcceptOrder(alreadyAcceptedOrderId, existedCourierId);

    Response response = steps.sendPutRequestAcceptOrder(alreadyAcceptedOrderId, existedCourierId);

    steps.compareResponseCodeAndMessageToExpectedValues(response, 409, "Этот заказ уже в работе");
  }

  @DisplayName("Принятие заказа с несуществующим id курьера возращает ошибку")
  @Test
  public void orderAcceptDeletedCourierReturnError() {
    //Создаем и удаляем курьера, чтоб получить несуществующий id
    Courier courier = new Courier(getLogin(), getPassword());
    courierSteps.sendPostRequestCreateCourier(courier);
    Response loginResponse = courierSteps.sendPostRequestLoginCourier(courier);
    int deletedCourierId = courierSteps.getCourierId(loginResponse);
    courierSteps.deleteCourier(deletedCourierId);

    Order order = new Order();
    int orderId = steps.orderCreateAndGetOrderIdAndTrackNumber(order).get("id");

    Response response = steps.sendPutRequestAcceptOrder(orderId, deletedCourierId);

    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Курьера с таким id не существует");
  }

  @DisplayName("Принятие заказа без номера заказа с id курьера в path возращает ошибку")
  @Test
  public void orderAcceptCourierIdInPathReturnError() {
    Response response = steps.sendPutRequestCourierIdInPathReturnError(existedCourierId);

    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для поиска");
  }

  @AfterClass
  public static void tearDown() {
    courierSteps.deleteCourier(existedCourierId);
  }
}
