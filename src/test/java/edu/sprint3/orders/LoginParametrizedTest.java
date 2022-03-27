package orders;


import edu.sprint3.orders.Order;
import edu.sprint3.orders.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class LoginParametrizedTest {
    private final List<String> color;
    private final boolean expected;


    public LoginParametrizedTest(List<String> color, boolean expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[] getData() {
        return new Object[][]{
                {List.of("BLACK", "GREY"), true},
                {List.of("BLACK"), true},
                {List.of("GREY"), true},
                {null, true}
        };
    }

    @Test
    public void createOrderTest() {
        OrderClient orderClient = new OrderClient();
        Order order = new Order(color);
        ValidatableResponse validatableResponse = orderClient.createOrder(order);
        Boolean actual = validatableResponse.extract().path("").toString().contains("track");

        Assert.assertEquals(actual, expected);
    }

}
