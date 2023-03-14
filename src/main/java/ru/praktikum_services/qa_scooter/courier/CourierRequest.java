package ru.praktikum_services.qa_scooter.courier;

import ru.praktikum_services.qa_scooter.constants.request.Header;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierRequest extends Header {

    private static final String COURIER_URL = BASE_URL + "courier/";

    @Step("Create courier {courierRegistrationFields}")
    public ValidatableResponse create(CourierRegistrationFields courierRegistrationFields) {
        return given()
                .spec(getRequestSpec())
                .body(courierRegistrationFields)
                .post(COURIER_URL).then();
    }

    @Step("Login courier {courierAuthorizationFields}")
    public ValidatableResponse login(CourierAuthorizationFields courierAuthorizationFields) {
        return given()
                .spec(getRequestSpec())
                .body(courierAuthorizationFields)
                .post(COURIER_URL + "login/").then();
    }

    @Step("Delete courier {id}")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getRequestSpec())
                .when()
                .delete(COURIER_URL + id)
                .then();
    }

    @Step("auth order {id}")
    public Response auth(CourierAuthorizationFields courierAuthorizationFields) {
        return given()
                .spec(getRequestSpec())
                .body(courierAuthorizationFields)
                .post(COURIER_URL + "login/");
    }
}
