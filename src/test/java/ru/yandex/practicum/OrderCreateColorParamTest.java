package ru.yandex.practicum;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.constants.Color;
import ru.yandex.practicum.objects.Order;
import ru.yandex.practicum.steps.OrderSteps;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static ru.yandex.practicum.constants.Color.BLACK;
import static ru.yandex.practicum.constants.Color.GREY;

@DisplayName("Создание заказа. Парметризация цветов самоката")
@RunWith(Parameterized.class)
public class OrderCreateColorParamTest extends BaseTest {
  Faker faker = new Faker(new Locale("ru"));
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
    order.setLastName(faker.name().lastName());
    order.setFirstName(faker.name().firstName());
    order.setAddress(faker.address().fullAddress());
    order.setMetroStation(faker.lorem().characters(10, 20));
    order.setPhone(faker.phoneNumber().cellPhone());
    order.setRentTime(faker.number().numberBetween(1,24));
    order.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(30, TimeUnit.DAYS)));
    order.setComment(faker.lorem().characters(10, 20));
    order.setColor(List.of(color));
    System.out.println(order.getAddress());
    System.out.println(order.getDeliveryDate());

    Response response = steps.sendPostRequestCreateOrder(order);

    steps.checkResponseBodyContainTrackValue(response);
  }
}
