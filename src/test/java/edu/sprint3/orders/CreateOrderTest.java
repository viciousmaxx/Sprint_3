package edu.sprint3.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderTest {
        private OrderClient orderClient;
        private Integer orderId;

        @Before
        public void setUp() {
            orderClient = new OrderClient();
        }

        @After
        public void tearDown() {
            orderClient.deleteOrder(orderId);
        }

        @Test
        @DisplayName("Создание заказа")
        @Description("Создаем заказ со случайными значениями без выбора цвета")
        public void orderCreatingIsAvailableTest() {
            Order order = Order.getRandomOrder();
            Response Response = orderClient.createOrder(order);
            orderId = Response.getBody().path("track");

            Response.then().assertThat().statusCode(201).and()
                    .body("", Matchers.hasKey("track"))
                    .body("track", Matchers.greaterThan(0));
        }
}
