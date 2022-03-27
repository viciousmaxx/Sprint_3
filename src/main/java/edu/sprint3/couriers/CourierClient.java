package edu.sprint3;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient{

    private static final String COURIER_PATH = "api/v1/courier/";
    @DisplayName("Creating courier")
    @Step("Creating courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().ifError();
//                .assertThat()
//                .statusCode(201)
//                .extract()
//                .path("ok");

    }

    @Step("Login courier {credentials.login}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then().log().ifError();
    }
    @Step("Delete courier {id}")
    public boolean delete(String id) {
        System.out.println("Удаляем курьера с ID " + id);
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
