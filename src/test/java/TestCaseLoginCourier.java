import constants.TestUser;
import courer_model.CourierAuthorizationFields;
import courer_model.CourierRegistrationFields;
import courer_model.CourierRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseLoginCourier {
    private CourierRequest courierRequest;
    private int courierIn;

    @Before
    public void setUp() {
        courierRequest = new CourierRequest();
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg);
    }

    @After
    public void clearDate() {
        CourierAuthorizationFields authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(authorization);
        courierIn = responseId.extract().path("id");
        courierRequest.delete(courierIn);
    }

    @Test // Логин курьера
    @DisplayName("The courier can log in")
    public void getLoginCourier() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());

        courierIn = responseId.extract().path("id");
    }

    @Test // Логин курьера
    @DisplayName("For authorization, you need to pass all the required fields - Login")
    public void getAuthorizationPassAllRequiredFieldsLogin() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields("", TestUser.PASSWORD);
        courierRequest.login(courier_authorization)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test // Логин курьера
    @DisplayName("For authorization, you need to pass all the required fields - Password")
    public void getAuthorizationPassAllRequiredFieldsPassword() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "");
        courierRequest.login(courier_authorization)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test //
    @DisplayName("Login or password is incorrect")
    public void getIncorrectUsernameOrPassword() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "12345");
        courierRequest.login(courier_authorization)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test //
    @DisplayName("No field, request returns an error")
    public void getRequestReturnsAnError() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "12345");
        courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(404);
    }


    @Test //
    @DisplayName("Log in as a non-existent user, the request returns an error")
    public void getNonExistentReturnsAnError() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "12345");
        courierRequest.login(courier_authorization)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test //
    @DisplayName("A successful request returns id.")
    public void getRequestReturnsId() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        courierRequest.login(courier_authorization)
                .assertThat()
                .body("id", notNullValue());
    }

}
