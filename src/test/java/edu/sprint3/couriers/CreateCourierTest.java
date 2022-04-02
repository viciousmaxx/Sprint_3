package edu.sprint3.couriers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;

import static org.junit.Assert.*;

public class CreateCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private ValidatableResponse courierId;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId.extract().path("id"));
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("При создании курьера возвращается статускод '201', а в теле ответа 'true'")
    public void courierCreatingIsAvailableTest() {

        ValidatableResponse isCreated = courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int actualStatusCode = isCreated.extract().statusCode();
        String actualId = isCreated.extract().path("ok").toString();

        assertEquals(201, actualStatusCode);
        assertEquals("true", actualId);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("При попытке создания курьера с дублирующими данными возвращается статускод 409, а в теле ответа 'Этот логин уже используется'")

    public void creatingTwoCourierWithSameCredentialsTest() {
        ValidatableResponse isCreated = courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        ValidatableResponse doubleCourier = courierClient.createCourier(courier);

        int actualStatusCode = doubleCourier.extract().statusCode();
        String actualAnswer = doubleCourier.extract().path("message").toString();

        assertEquals(409, actualStatusCode);
        assertEquals("Этот логин уже используется", actualAnswer);
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля;
    @Test
    @DisplayName("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @Description("При попытке создания курьера без указания пароля возвращается статускод 400, а в теле ответа 'Недостаточно данных для создания учетной записи'")

    public void creatingCourierWithNotFulfilledCredentialsNotAllowedTest() {
        Courier invalidCourier = new Courier();
        invalidCourier.setLogin(courier.login);
        invalidCourier.setPassword("");
        invalidCourier.setFirstName(courier.password);
        ValidatableResponse isCreated = courierClient.createCourier(invalidCourier);

        int actualStatusCode = isCreated.extract().statusCode();
        String actualAnswer = isCreated.extract().path("message");

        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для создания учетной записи", actualAnswer);
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    @Description("При создании курьера возвращается статускод '201'")

    public void courierCreatingReturnStatusCode200Test() {
        ValidatableResponse isCreated = courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int actual = isCreated.extract().statusCode();

        assertEquals(201, actual);
    }

    //успешный запрос возвращает ok: true;
    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    @Description("При создании курьера возвращается ответ 'ok: true'")
    public void successRequestReturnOkTrueTest() {
        ValidatableResponse isCreated = courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        String actual = isCreated.extract().path("ok").toString();
        assertEquals("true", actual);
    }

    @Test
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку")
    @Description("При создании курьера без указания firstName возвращается статускод '400' и ответ \"message\": \"Недостаточно данных для создания учетной записи\"")
    public void creatingCourierWithNotAllCredentialsDataNotAllowedTest() {
        Courier invalidCourier = new Courier();
        invalidCourier.setLogin(courier.login);
        invalidCourier.setFirstName(courier.password);
        ValidatableResponse isCreated = courierClient.createCourier(invalidCourier);

        int actualStatusCode = isCreated.extract().statusCode();
        String actualAnswer = isCreated.extract().path("message");

        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для создания учетной записи", actualAnswer);
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Description("Создание курьера с уже имеющимся логином возвращается статускод '409' и ответ \"message\": \"Этот логин уже используется\"")

    public void creatingCourierWithExistingLoginNotAllowedTest() {
        ValidatableResponse isCreated = courierClient.createCourier(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        Courier courierWithSameLogin = new Courier();
        courierWithSameLogin.setLogin(courier.login);
        courierWithSameLogin.setPassword(RandomStringUtils.randomAlphabetic(10));
        courierWithSameLogin.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse isNotCreated = courierClient.createCourier(courierWithSameLogin);

        int actualStatusCode = isNotCreated.extract().statusCode();
        String actualAnswer = isNotCreated.extract().path("message");

        assertEquals(409, actualStatusCode);
        assertEquals("Этот логин уже используется", actualAnswer);
    }
}
