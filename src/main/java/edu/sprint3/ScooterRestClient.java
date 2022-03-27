package edu.sprint_3;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class ScooterRestClient {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();

    }
}

