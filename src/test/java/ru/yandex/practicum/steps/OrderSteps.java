package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.objects.Order;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.constants.Routes.*;

public class OrderSteps extends BaseSteps {
  @Step("Отправка post запроса " + ORDER_CREATE_ROUTE + " на создание заказа")
  public Response sendPostRequestCreateOrder(Order order) {
    return
      given().
        contentType("application/json").
        body(order).
        when().
        post(ORDER_CREATE_ROUTE);
  }

  @Step("Отправка get запроса" + ORDER_INFO_ROUTE + " на получение заказа")
  public Response sendGetRequestTrackOrder(int track) {
    return
      given().
        contentType("application/json").
        queryParam("t", track).
        when().
        get(ORDER_INFO_ROUTE);
  }

  @Step("Отправка get запроса" + ORDER_INFO_ROUTE + " на получение заказа")
  public Response sendGetRequestTrackOrder() {
    return
      given().
        when().
        get(ORDER_INFO_ROUTE);
  }

  @Step("Отправка put запроса" + ORDER_ACCEPT_ROUTE + " на принятие заказа")
  public Response sendPutRequestAcceptOrder(int orderId, int courierId) {
    return
      given().
        queryParam("courierId", courierId).
        pathParam("orderId", orderId).
        when().
        put(ORDER_ACCEPT_ROUTE + "/{orderId}");
  }

  @Step("Отправка put запроса" + ORDER_ACCEPT_ROUTE + " на принятие заказа без id курьера")
  public Response sendPutRequestAcceptOrder(int orderId) {
    return
      given().
        pathParam("orderId", orderId).
        when().
        put(ORDER_ACCEPT_ROUTE + "/{orderId}");
  }

  @Step("Отправка put запроса" + ORDER_ACCEPT_ROUTE + " на принятие заказа без id заказа")
  public Response sendPutRequestAcceptOrder(Integer courierId) {
    return
      given().
        queryParam("courierId", courierId).
        when().
        put(ORDER_ACCEPT_ROUTE);
  }

  @Step("Отправка put запроса" + ORDER_ACCEPT_ROUTE + " на принятие заказа с id курьера в пути")
  public Response sendPutRequestCourierIdInPathReturnError(int courierId) {
    return
      given().
        contentType("application/json").
        pathParam("courierId", courierId).
        when().
        put(ORDER_ACCEPT_ROUTE + "/{courierId}");
  }

  @Step("Отправка put запроса" + ORDER_CANCEL_ROUTE + " на отмену заказа")
  public Response sendPutRequestCancelOrder(int track) {
    return
      given().
        contentType("application/json").
        param("track", track).
        when().
        put(ORDER_CANCEL_ROUTE);
  }

  @Step("Получение трек-номера после создания заказа")
  public int getTrackNumber(Response orderCreateResponse) {
    return orderCreateResponse.
      then().
      extract().
      path("track");
  }

  @Step("Получение id заказа по трек номеру")
  private int getOrderIdByTrackNumber(int track) {
    Response response = sendGetRequestTrackOrder(track);
    return response.
      then().
      extract().
      path("order.id");
  }

  @Step("Создание нового заказа и получение его id и трек-номера")
  public Map<String, Integer> orderCreateAndGetOrderIdAndTrackNumber(Order order) {
    Map<String, Integer> map = new HashMap<>();
    Response response = sendPostRequestCreateOrder(order);
    int trackNumber = getTrackNumber(response);
    map.put("track", trackNumber);
    map.put("id", getOrderIdByTrackNumber(trackNumber));
    return map;
  }

  @Step("Проверка, что ответ содержит ключ track")
  public void checkResponseBodyContainTrackValue(Response response) {
    response.
      then().
      statusCode(201).
      body("track", notNullValue());
  }

  @Step("Проверка, что ответ содержит заказ")
  public Order checkResponseContainOrder(Response response) {
    return response.then().extract().jsonPath().getObject("order", Order.class);
  }

  @Step("Проверка, что содержимое заказа совпадает")
  public void compareOrders(Order existedOrder, Order orderFromResponse) {
    assertTrue(existedOrder.equals(orderFromResponse));
  }
}
