import constants.TestUser;
import courer_model.CourierAuthorizationFields;
import courer_model.CourierRegistrationFields;
import courer_model.CourierRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseCreateCourier {

    private CourierRequest courierRequest;
    private int courierIn;

    @Before
    public void setUp() {
        courierRequest = new CourierRequest();
    }

    @After
    public void clearDate() {
        courierRequest.delete(courierIn);
    }

    @Test //
    @DisplayName("Checking if a courier can be created")
    public void getTestCreateCourier() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(201)
                .and()
                .assertThat().body("ok", is(true));

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(courier_authorization)
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());

        courierIn = responseId.extract().path("id");
    }

    @Test
    @DisplayName("Check, you cannot create two identical couriers. Error text: Этот логин уже используется")
    public void getTestLoginYesReturnError() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(201);

        courierRequest.create(courier_reg)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(courier_authorization);
        courierIn = responseId.extract().path("id");
    }

    @Test
    @DisplayName("Checking that login is a required field. Insufficient data to create an account")
    public void getAllRequiredFieldsLogin() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields("", TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking that the password field is a required field.Insufficient data to create an account")
    public void getAllRequiredFieldsPassword() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, "", TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking if the field name is not a required field")
    public void getAllRequiredFieldsFirstName() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, "");
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(201);
    }

    @Test //
    @DisplayName("The request returns the correct response code")
    public void getCourierCanBeCreated() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(201);

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(courier_authorization);
        courierIn = responseId.extract().path("id");
    }

    @Test //
    @DisplayName("A successful request returns ok: true;")
    public void getSuccessfulRequestReturns() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .body("ok", equalTo(true));

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(courier_authorization);
        courierIn = responseId.extract().path("id");
    }

    @Test //
    @DisplayName("If one of the fields is missing, the query returns an error")
    public void getRequestReturnsAnError() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, "", TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(400);
    }

    @Test //
    @DisplayName("Check for uniqueness. Error code 409")
    public void getYouCannotCreateTwoIdenticalCouriers() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg);

        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(409);

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        ValidatableResponse responseId = courierRequest.login(courier_authorization);
        courierIn = responseId.extract().path("id");

    }
}
