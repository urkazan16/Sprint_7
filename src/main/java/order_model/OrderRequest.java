package order_model;

import constants.request.Header;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderRequest extends Header {

    private static final String COURIER_URL = BASE_URL + "orders/";

    @Step("Create order {orderFields}")
    public ValidatableResponse create(OrderFields orderFields) {
        return given()
                .spec(getRequestSpec())
                .body(orderFields)
                .post(COURIER_URL).then();
    }

    @Step("Get orders /orders")
    public ValidatableResponse get() {
        return given()
                .spec(getRequestSpec())
                .get(COURIER_URL).then();
    }

    @Step("Get create orders {order_id}")
    public ValidatableResponse getOrder(int order_id) {
        return given()
                .spec(getRequestSpec())
                .get(COURIER_URL + "track?t=" + order_id).then();
    }


    @Step("Cancel order {id}")
    public ValidatableResponse cancel(int id) {
        return given()
                .spec(getRequestSpec())
                .put(COURIER_URL + "cancel?track=" + id).then();
    }

}
