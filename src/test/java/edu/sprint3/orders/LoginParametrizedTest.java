package edu.sprint3.orders;


import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginParametrizedTest {
    private final List<String> color;
    private final boolean expected;


    public LoginParametrizedTest(List<String> color, boolean expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: color = {0}")
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
        Response response = orderClient.createOrder(order);
        int track = response.getBody().path("track");
        boolean actual = track > 0;

        assertEquals(expected, actual);
    }

}
