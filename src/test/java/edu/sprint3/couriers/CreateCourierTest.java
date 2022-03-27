package edu.sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CreateCourierTest {

    private CourierClient courierClient;
    private ValidatableResponse courierId;

    @Before
    public void setUp() { courierClient = new CourierClient(); }

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


    //курьера можно создать
    @Test
    @DisplayName("Check user name and print response body")
    @Description("This is a more complicated test with console output")
    public void courierCreatingIsAvailableTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int actual = isCreated.extract().statusCode();
        assertEquals(201, actual);
        assertNotEquals(0, courierId);
    }

    //нельзя создать двух одинаковых курьеров
    @Test
    public void creatingTwoCourierWithSameCredentialsTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        ValidatableResponse doubleCourier = courierClient.create(courier);

        assertEquals(409, doubleCourier.extract().statusCode());
        assertEquals(doubleCourier.extract().path("message"),
                "Этот логин уже используется");
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля;
    @Test
    public void creatingCourierWithNotFulfilledAllCredentialsNotAllowedTest() {
        Courier courier = Courier.getRandomCourier();
        Courier invalidCourier = new Courier();
        invalidCourier.setLogin(courier.login);
        invalidCourier.setPassword("");
        invalidCourier.setFirstName(courier.password);
        ValidatableResponse isCreated = courierClient.create(invalidCourier);

        assertEquals(400, isCreated.extract().statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", isCreated.extract().path("message"));
    }

    //запрос возвращает правильный код ответа;
    @Test
    public void courierCreatingReturnStatusCode200Test() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int actual = isCreated.extract().statusCode();
        assertEquals(201, actual);
    }

    //успешный запрос возвращает ok: true;
    @Test
    public void successRequestReturnOkTrueTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        String actual = isCreated.extract().path("ok").toString();
        assertEquals("true", actual);
    }

    //если одного из полей нет, запрос возвращает ошибку;
    @Test
    public void creatingCourierWithNotAllCredentialsDataNotAllowedTest() {
        Courier courier = Courier.getRandomCourier();
        Courier invalidCourier = new Courier();
        invalidCourier.setLogin(courier.login);
        invalidCourier.setFirstName(courier.password);
        ValidatableResponse isCreated = courierClient.create(invalidCourier);

        assertEquals("Недостаточно данных для создания учетной записи", isCreated.extract().path("message"));
    }

    //если создать пользователя с логином, который уже есть, возвращается ошибка.
    @Test
    public void creatingCourierWithExistingLoginNotAllowedTest() {
        Courier courier = Courier.getRandomCourier();
        ValidatableResponse isCreated = courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        Courier courierWithSameLogin = new Courier();
        courierWithSameLogin.setLogin(courier.login);
        courierWithSameLogin.setPassword(RandomStringUtils.randomAlphabetic(10));
        courierWithSameLogin.setFirstName(RandomStringUtils.randomAlphabetic(10));

        ValidatableResponse isNotCreated = courierClient.create(courierWithSameLogin);

        assertEquals(409, isNotCreated.extract().statusCode());
        assertEquals(isNotCreated.extract().path("message"),
                "Этот логин уже используется");
    }


}
