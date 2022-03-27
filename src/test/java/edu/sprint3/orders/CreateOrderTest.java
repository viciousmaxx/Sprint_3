package orders;

import edu.sprint3.orders.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class CreateOrderTest {
        private OrderClient orderClient;
        private String orderId;

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
            ValidatableResponse validatableResponse = orderClient.createOrder(order);
            orderId = validatableResponse.extract().path("track").toString();
            int actual = validatableResponse.extract().statusCode();

            assertEquals(201, actual);
        }
}
