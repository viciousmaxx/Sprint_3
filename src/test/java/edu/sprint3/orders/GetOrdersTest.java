package edu.sprint3.orders;

import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class GetOrdersTest {

    @Test
    public void getOrdersTest() {

        OrderClient orderClient = new OrderClient();
        Response response = orderClient.getOrders();

                response.then().assertThat().statusCode(200).and()
                        //наличие массива и его размер
                        .body("size()", is(3))
                        //формат id элементов массива
                        .body("orders.id", everyItem(isA(Integer.class)))
                        .log().ifError();
    }
}
