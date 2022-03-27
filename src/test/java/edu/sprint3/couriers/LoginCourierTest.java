package edu.sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginCourierTest {

    private CourierClient courierClient;
    private ValidatableResponse courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        try {
            if ((int) (courierId.extract().path("id")) > 0) {
                courierClient.delete(courierId.extract().path("id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //курьер может авторизоваться;
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void CourierCanLoginTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        assertNotNull(courierId.extract().path("id"));
        assertEquals(200, courierId.extract().statusCode());
    }

    //для авторизации нужно передать все обязательные поля;
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void forAuthorisationAllNeededAllRequiredFieldsTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        courier.setPassword("");
        ValidatableResponse wrongCourierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        assertEquals(400, wrongCourierId.extract().statusCode());
        assertEquals("Недостаточно данных для входа", wrongCourierId.extract().path("message"));
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void wrongCredentialsReturnErrorTest() {
        Courier courier = Courier.getRandomCourier();

        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        String wrongPassword = RandomStringUtils.randomAlphabetic(10);
        courier.setPassword(wrongPassword);
        ValidatableResponse wrongCourierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        assertNotNull("Учетная запись не найдена", wrongCourierId.extract().path("message"));
        assertEquals(404, wrongCourierId.extract().statusCode());
    }

    //если какого-то поля нет, запрос возвращает ошибку;
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void forAuthorisationAllDataNeededTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin()));

        assertEquals(400, courierId.extract().statusCode());
        assertEquals("Недостаточно данных для входа", courierId.extract().path("message"));

    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void nonExistedUsersNotAllowedToAuthorisationTest() {
        Courier courier = Courier.getRandomCourier();
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        assertEquals(404, courierId.extract().statusCode());
        assertEquals("Учетная запись не найдена", courierId.extract().path("message"));
    }

    //успешный запрос возвращает id.
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void successRequestReturnsIdTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        assertTrue(courierId.extract().path("").toString().contains("id"));
        assertEquals(200, courierId.extract().statusCode());
    }
}