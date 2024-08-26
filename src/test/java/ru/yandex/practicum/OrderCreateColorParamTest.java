package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.constants.Color;
import ru.yandex.practicum.objects.Order;
import ru.yandex.practicum.steps.OrderSteps;

import java.util.List;

import static ru.yandex.practicum.constants.Color.BLACK;
import static ru.yandex.practicum.constants.Color.GREY;

@DisplayName("Создание заказа. Парметризация цветов самоката")
@RunWith(Parameterized.class)
public class OrderCreateColorParamTest extends BaseTest {
  private Color[] color;
  private String displayName;

  public OrderCreateColorParamTest(Color[] color, String displayName) {
    this.color = color;
    this.displayName = displayName;
  }

  OrderSteps steps = new OrderSteps();

  @Parameterized.Parameters(name = "Создание заказа с обязательными полями {1} проходит успешно")
  public static Object[][] setParams() {
    return new Object[][]{
      {new Color[]{BLACK}, "черный"},
      {new Color[]{GREY}, "серый"},
      {new Color[]{GREY, BLACK}, "любой цвет"},
      {new Color[]{}, "цвет не задан"}
    };
  }

  @Test
  public void createOrderRequiredFieldsCreatesOrder() {
    Order order = new Order();
    order.setLastName("Иванов");
    order.setFirstName("Иван");
    order.setAddress("проспект Художников 24");
    order.setMetroStation("Озерки");
    order.setPhone("+7 950 2225455");
    order.setRentTime(12);
    order.setDeliveryDate("2024-09-06");
    order.setComment("доп. телефон для связи 8 950 123 5677");
    order.setColor(List.of(color));

    Response response = steps.sendPostRequestCreateOrder(order);

    steps.checkResponseBodyContainTrackValue(response);
  }
}
