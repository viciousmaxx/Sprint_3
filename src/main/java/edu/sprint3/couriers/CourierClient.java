package edu.sprint3.couriers;

import edu.sprint3.ScooterRestClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "api/v1/courier/";
    @DisplayName("Создание курьера")
    @Step("Регистрация курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().ifError();
    }
    @DisplayName("Авторизация курьера")
    @Step("Авторизуем курьера {credentials.login}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then().log().ifError();
    }
    @DisplayName("Удаление курьера")
    @Step("Удаляем курьера с ID {id}")
    public boolean delete(Integer id) {
        return given().log().ifValidationFails()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + id)
                .then().log().ifError()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

}
