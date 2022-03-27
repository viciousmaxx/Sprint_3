package orders;

import edu.sprint3.orders.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;


public class GetOrdersTest {

    @Test
    public void getOrdersTest() {

        OrderClient orderClient = new OrderClient();
        ValidatableResponse validatableResponse = orderClient.getOrders();

        Assert.assertTrue(validatableResponse.extract().body().jsonPath().get("").toString().contains("orders"));
    }


}
