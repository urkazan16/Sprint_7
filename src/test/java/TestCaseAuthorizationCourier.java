import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.constants.TestCourier;
import ru.praktikum_services.qa_scooter.courier.CourierAuthorizationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRegistrationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRequest;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseAuthorizationCourier {
    private CourierRequest courierRequest;
    private int responseLoginId;
    private CourierRegistrationFields courierRegistration;

    @Before
    public void setUp() {
        courierRequest = new CourierRequest();
        courierRegistration = TestCourier.getRandom();
        courierRequest.create(courierRegistration);
    }

    @After
    public void clearDate() {
        courierRequest.delete(responseLoginId);
    }

    @Test // Курьера авторизовываться
    @DisplayName("The courier can log in")
    public void getLoginCourier() {
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        responseLoginId = response.extract().path("id");
        response.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test // Логин курьера
    @DisplayName("For authorization, you need to pass all the required fields - Login")
    public void getAuthorizationPassAllRequiredFieldsLogin() {
        courierRegistration.setLogin(null);
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test // Логин курьера
    @DisplayName("For authorization, you need to pass all the required empty fields - No login")
    public void getAuthorizationPassAllRequiredFieldsNoLogin() {
        courierRegistration.setLogin("");
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test // Пароль курьера
    @DisplayName("For authorization, you need to pass all the required fields - Password")
    public void getAuthorizationPassAllRequiredFieldsPassword() {
        courierRegistration.setPassword(null);
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test // Пароль курьера
    @DisplayName("For authorization, you need to pass all the required empty fields - No password")
    public void getAuthorizationPassAllRequiredFieldsNoPassword() {
        courierRegistration.setPassword("");
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test //
    @DisplayName("Login or password is incorrect")
    public void getIncorrectUsernameOrPassword() {
        courierRegistration.setPassword("12345");
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //
    @DisplayName("No field, request returns an error and Log in as a non-existent user, the request returns an error")
    public void getRequestReturnsAnError() {
        courierRegistration.setPassword("12345");
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //
    @DisplayName("A successful request returns id.")
    public void getRequestReturnsId() {
        ValidatableResponse response = courierRequest.login(CourierAuthorizationFields.from(courierRegistration));
        responseLoginId = response.extract().path("id");
        response.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

}
