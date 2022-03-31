package edu.sprint3.orders;

import edu.sprint3.ScooterRestClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

    public class OrderClient extends ScooterRestClient {

        private static final String ORDER_PATH = "api/v1/orders/";

        @DisplayName("Создание заказа")
        @Step("Регистрируем заказ")
        public Response createOrder(Order order) {
            return given()
                    .spec(getBaseSpec())
                    .body(order)
                    .when()
                    .post(ORDER_PATH);
        }

        @DisplayName("Запрашиваем заказы")
        @Step("Получаем список заказов")
        public Response getOrders() {
            return given()
                    .spec(getBaseSpec())
                    .when()
                    .get(ORDER_PATH);
        }
        @DisplayName("Удаление заказ")
        @Step("Удаляем заказ {orderId}")
        public void deleteOrder(Integer orderId) {
             given()
                    .spec(getBaseSpec())
                    .put(ORDER_PATH + "cancel?track={orderId}", orderId);
        }
    }

