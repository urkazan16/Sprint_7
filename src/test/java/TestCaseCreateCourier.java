import ru.praktikum_services.qa_scooter.constants.TestUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import ru.praktikum_services.qa_scooter.courier.CourierAuthorizationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRegistrationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
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
        Response responseId = courierRequest.auth(courier_authorization);
        courierIn = responseId.path("id");
    }

    @Test
    @DisplayName("Check, you cannot create two identical couriers. Error text: Этот логин уже используется and Error code 409")
    public void getTestLoginYesReturnError() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg);

        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        Response responseId = courierRequest.auth(courier_authorization);
        courierIn = responseId.path("id");
    }

    @Test
    @DisplayName("Checking that login is a required field. Insufficient data to create an account and returns an error 400")
    public void getAllRequiredFieldsLogin() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields("", TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking that the password field is a required field.Insufficient data to create an account  and returns an error 400")
    public void getAllRequiredFieldsPassword() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, "", TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking if the field name is not a required field and returns ok: true;")
    public void getAllRequiredFieldsFirstName() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, "");
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        Response responseId = courierRequest.auth(courier_authorization);
        courierIn = responseId.path("id");
    }

    @Test //
    @DisplayName("The request returns the correct response code and returns ok: true")
    public void getCourierCanBeCreated() {
        CourierRegistrationFields courier_reg = new CourierRegistrationFields(TestUser.LOGIN, TestUser.PASSWORD, TestUser.FIRST_NAME);
        courierRequest.create(courier_reg)
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
        ;

        CourierAuthorizationFields courier_authorization = new CourierAuthorizationFields(TestUser.LOGIN, TestUser.PASSWORD);
        Response responseId = courierRequest.auth(courier_authorization);
        courierIn = responseId.path("id");
    }

}
