import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.constants.TestCourier;
import ru.praktikum_services.qa_scooter.courier.CourierAuthorizationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRegistrationFields;
import ru.praktikum_services.qa_scooter.courier.CourierRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestCaseRegistrationCourier {

    public int responseId;
    private CourierRequest courierRequest;

    @Before
    public void setUp() {
        courierRequest = new CourierRequest();
    }

    @After
    public void clearDate() {
        if (responseId != 0) {
            courierRequest.delete(responseId);
        }
    }

    @Test //
    @DisplayName("Checking if a courier can be created")
    public void getTestCreateCourier() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        ValidatableResponse response = courierRequest.create(courierFields);
        responseId = courierRequest.auth(CourierAuthorizationFields.from(courierFields)).path("id");
        response.assertThat()
                .statusCode(201)
                .and()
                .assertThat().body("ok", is(true));
    }

    @Test
    @DisplayName("Check, you cannot create two identical couriers. Error text: Этот логин уже используется and Error code 409")
    public void getTestLoginYesReturnError() {

        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierRequest.create(courierFields);
        ValidatableResponse response = courierRequest.create(courierFields);
        responseId = courierRequest.auth(CourierAuthorizationFields.from(courierFields)).path("id");
        response.assertThat()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется."));
    }

    @Test
    @DisplayName("Checking that login is a required field. Insufficient data to create an account and returns an error 400")
    public void getAllRequiredFieldsLogin() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierFields.setLogin(null);
        courierRequest.create(courierFields)
                .assertThat()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking that no login is a required field. Insufficient data to create an account and returns an error 400")
    public void getAllRequiredFieldsNoLogin() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierFields.setLogin("");
        courierRequest.create(courierFields)
                .assertThat()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking that the password field is a required field.Insufficient data to create an account  and returns an error 400")
    public void getAllRequiredFieldsPassword() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierFields.setPassword(null);
        courierRequest.create(courierFields)
                .assertThat()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Checking that the no password field is a required field.Insufficient data to create an account  and returns an error 400")
    public void getAllRequiredFieldsNoPassword() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierFields.setPassword("");
        courierRequest.create(courierFields)
                .assertThat()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test //
    @DisplayName("The request returns the correct response code and returns ok: true")
    public void getCourierCanBeCreated() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        ValidatableResponse response = courierRequest.create(courierFields);
        responseId = courierRequest.auth(CourierAuthorizationFields.from(courierFields)).path("id");
        response.assertThat()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Checking if the field name is not a required field and returns ok: true;")
    public void getAllRequiredFieldsFirstName() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierFields.setFirstName(null);
        ValidatableResponse response = courierRequest.create(courierFields);
        responseId = courierRequest.auth(CourierAuthorizationFields.from(courierFields)).path("id");
        response.assertThat()
                .statusCode(201)
                .and()
                .assertThat().body("ok", is(true));

    }

    @Test
    @DisplayName("Checking if the field name is not a required field and returns ok: true;")
    public void getAllRequiredFieldsNoFirstName() {
        CourierRegistrationFields courierFields = TestCourier.getRandom();
        courierFields.setFirstName("");
        ValidatableResponse response = courierRequest.create(courierFields);
        responseId = courierRequest.auth(CourierAuthorizationFields.from(courierFields)).path("id");
        response.assertThat()
                .statusCode(201)
                .and()
                .assertThat().body("ok", is(true));

    }

}
