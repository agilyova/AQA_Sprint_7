package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.objects.Order;
import ru.yandex.practicum.steps.OrderSteps;

@DisplayName("Получение заказа по трек-номеру")
public class OrderGetTest extends BaseTest {
  int notExistedTrack = 1;
  OrderSteps steps = new OrderSteps();

  @DisplayName("Получение заказа по существующему трек-трек номеру возвращает информацию по заказу")
  @Test
  public void getOrderByTrackNumberReturnOrder() {
    Order order = new Order();
    order.setFirstName("Иванов");
    order.setLastName("Иван");
    Response orderCreateResponse = steps.sendPostRequestCreateOrder(order);
    int existedOrderTrack = steps.getTrackNumber(orderCreateResponse);
    order.setTrack(existedOrderTrack);

    Response response = steps.sendGetRequestTrackOrder(existedOrderTrack);

    Order orderFromResponse = steps.checkResponseContainOrder(response);
    steps.compareOrders(order, orderFromResponse);
  }

  @DisplayName("Получение заказа без трек-номера возвращает ошибку")
  @Test
  public void getOrderWithoutTrackNumberReturnError() {
    Response response = steps.sendGetRequestTrackOrder();

    steps.compareResponseCodeAndMessageToExpectedValues(response, 400, "Недостаточно данных для поиска");
  }

  @DisplayName("Получение заказа по несуществующему трек-номеру возращает ошибку")
  @Test
  public void getOrderNotExistedTrackNumberReturnError() {
    Response response = steps.sendGetRequestTrackOrder(notExistedTrack);

    steps.compareResponseCodeAndMessageToExpectedValues(response, 404, "Заказ не найден");
  }
}
