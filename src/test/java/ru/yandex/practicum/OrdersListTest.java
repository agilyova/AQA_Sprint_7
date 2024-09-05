package ru.yandex.practicum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.objects.Orders;

import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertFalse;
import static ru.yandex.practicum.constants.Routes.ORDER_LIST_ROUTE;

@DisplayName("Получение списка заказов")
public class OrdersListTest extends BaseTest {
  @DisplayName("Запрос всех заказов без доп.фильтрации возвращает список заказов")
  @Test
  public void getOrderListReturnsOrders() {
    Response response = sentGetOrdersRequest();

    assertStatusCodeAndReturnsOrders(response);

    //List<Order> orders = given().when().get(ORDER_LIST_ROUTE).then().extract().jsonPath().getList("orders", Order.class);
    //Assert.assertTrue(!orders.isEmpty());
  }

  @Step("Отправка get запроса " + ORDER_LIST_ROUTE + " на получение списка заказов")
  public Response sentGetOrdersRequest() {
    Response response = when().get(ORDER_LIST_ROUTE);
    return response;
  }

  @Step("Проверка статус кода и что возвращается непустой список заказов")
  public void assertStatusCodeAndReturnsOrders(Response response) {
    response.then().statusCode(200);
    Orders orders = response.body().as(Orders.class);
    assertFalse(orders.getOrders().isEmpty());
  }
}
