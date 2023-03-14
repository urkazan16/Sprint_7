import ru.praktikum_services.qa_scooter.constants.TestUser;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.courier.CourierAuthorizationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRegistrationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseLoginCourier {
    private CourierRequest courierRequest;
    private int responseLoginId;

    @Before
    public void setUp() {
        courierRequest = new CourierRequest();
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg);
    }

    @After
    public void clearDate() {
        courierRequest.delete(responseLoginId);
    }

    @Test // Курьера авторизовываться
    @DisplayName("The courier can log in")
    public void getLoginCourier() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        responseLoginId = courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue()).extract().path("id");
    }

    @Test // Логин курьера
    @DisplayName("For authorization, you need to pass all the required fields - Login")
    public void getAuthorizationPassAllRequiredFieldsLogin() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields("", TestUser.PASSWORD);
        courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test // Пароль курьера
    @DisplayName("For authorization, you need to pass all the required fields - Password")
    public void getAuthorizationPassAllRequiredFieldsPassword() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "");
        courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test //
    @DisplayName("Login or password is incorrect")
    public void getIncorrectUsernameOrPassword() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "12345");
        courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test //
    @DisplayName("No field, request returns an error and Log in as a non-existent user, the request returns an error")
    public void getRequestReturnsAnError() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, "12345");
        courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //
    @DisplayName("A successful request returns id.")
    public void getRequestReturnsId() {
        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        responseLoginId = courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue()).extract().path("id");
    }

}
