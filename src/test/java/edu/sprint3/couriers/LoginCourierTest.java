package edu.sprint3.couriers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;

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
        if (courierId != null) {
            boolean x = courierClient.delete(courierId.extract().path("id"));
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("При авторизации возвращается статускод '200' и ответ с id:")
    public void CourierCanLoginTest() {
        Courier courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int actualStatusCode = courierId.extract().statusCode();
        String actualAnswer = courierId.extract().path("id").toString();

        assertNotNull(actualAnswer);
        assertEquals(200, actualStatusCode);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля")
    @Description("При авторизации с пустым значением пароля возвращается статускод '400' и ответ с \"message\":  \"Недостаточно данных для входа\"")
    public void forAuthorisationAllNeededAllRequiredFieldsTest() {
        Courier courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courier.setPassword("");
        ValidatableResponse wrongCourierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int actualStatusCode = wrongCourierId.extract().statusCode();
        String actualAnswer = wrongCourierId.extract().path("message").toString();

        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для входа", actualAnswer);
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль")
    @Description("Авторизация с некорректным паролем возвращается статускод '404' и ответ \"message\": \"Учетная запись не найдена\"")
    public void wrongCredentialsReturnErrorTest() {
        Courier courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse wrongCourierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        String actualAnswer = wrongCourierId.extract().path("message").toString();
        int actualStatusCode = wrongCourierId.extract().statusCode();

        assertEquals("Учетная запись не найдена", actualAnswer);
        assertEquals(404, actualStatusCode);
    }

    @Test
    @DisplayName("Если какого-то поля нет, запрос возвращает ошибку")
    @Description("Авторизация с пустым паролем возвращается статускод '400'")
    public void forAuthorisationAllDataNeededTest() {

        Courier courier = new Courier();
        courier.setLogin("login");
        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.getLogin()));
        int actualStatusCode = validatableResponse.extract().statusCode();

        assertEquals(400, actualStatusCode);
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;")
    @Description("Авторизация с некорректной парой логин/пароль возвращает статускод '404' и ответ \"message\": \"Учетная запись не найдена\"")
    public void nonExistedUsersNotAllowedToAuthorisationTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse fakeCourierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int actualStatusCode = fakeCourierId.extract().statusCode();
        String actualAnswer = fakeCourierId.extract().path("message").toString();

        assertEquals(404, actualStatusCode);
        assertEquals("Учетная запись не найдена", actualAnswer);
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    @Description("Успешная авторизация с корректной парой логин/пароль возвращает ответ с id:")
    public void successRequestReturnsIdTest() {
        Courier courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        boolean actualAnswer = courierId.extract().path("").toString().contains("id");

        assertTrue(actualAnswer);
    }
}