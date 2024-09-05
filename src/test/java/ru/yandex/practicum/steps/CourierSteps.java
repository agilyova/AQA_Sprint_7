package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.objects.Courier;

import static io.restassured.RestAssured.given;
import static ru.yandex.practicum.constants.Routes.*;

public class CourierSteps extends BaseSteps {

  @Step("Отправка post запроса " + COURIER_CREATE_ROUTE + " на создание курьера")
  public Response sendPostRequestCreateCourier(Courier courier) {
    return
      given().
        contentType("application/json").
        body(courier).
        when().
        post(COURIER_CREATE_ROUTE);
  }

  @Step("Отправка post запроса " + COURIER_LOGIN_ROUTE + " на авторизацию курьера")
  public Response sendPostRequestLoginCourier(Courier courier) {
    return
      given().
        contentType("application/json").
        body(courier).
        when().
        post(COURIER_LOGIN_ROUTE);
  }

  @Step("Получение id курьера из ответа на запрос логина курьера")
  public int getCourierId(Response loginResponse) {
    return
      loginResponse.
        then().
        extract().
        path("id");
  }

  @Step("Отправка get запроса на удаление курьера по id")
  public Response sendDeleteRequestDeleteCourier(int courierId) {
    return
      given().
        contentType("application/json").
        pathParam("id", courierId).
        when().
        delete(COURIER_DELETE_ROUTE + "/{id}");
  }

  @Step("Отправка get запроса на удаление курьера без id")
  public Response sendDeleteRequestDeleteCourier() {
    return
      given().
        contentType("application/json").
        when().
        delete(COURIER_DELETE_ROUTE);
  }

  @Step("Создание курьера и получение его id")
  public int createCourierAndGetItId(Courier courier) {
    sendPostRequestCreateCourier(courier);
    Response response = sendPostRequestLoginCourier(courier);
    return getCourierId(response);
  }

  @Step("Удаление курьера, когда id курьера не известен")
  public void deleteCourier(Courier courier) {
    Response loginResponse = sendPostRequestLoginCourier(courier);
    int courierId = getCourierId(loginResponse);
    Response deleteResponse = sendDeleteRequestDeleteCourier(courierId);
    compareResponseCodeAndOkValue(deleteResponse, 200, true);
  }

  @Step("Удаление курьера по id")
  public void deleteCourier(int courierId) {
    Response deleteResponse = sendDeleteRequestDeleteCourier(courierId);
    compareResponseCodeAndOkValue(deleteResponse, 200, true);
  }
}
