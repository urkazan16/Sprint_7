import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.order.OrderFields;
import ru.praktikum_services.qa_scooter.order.OrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCaseCreateOrder {

    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List color;
    private OrderRequest orderRequest;
    private int orderIn;

    public TestCaseCreateOrder(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    //    @Parameterized.Parameters( name = "Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7} {8}" )
    @Parameterized.Parameters(name = "Создание заказа. Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7} {8}")
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Максим", "Жадобов", "г.Москва ул.Груздева д.5", 5, "+7905314620", 5, "2020-06-06", "", List.of("")},
                {"Иванко", "Иванко", "г.Москва ул.Петровская д.6", 5, "+7903454351", 1, "2020-06-06", "Сгенерируй тестовые данные (свою учётку и несколько случайных)", List.of("BLACK")},
                {"Петровски", "Петровски", "г.Москва ул.Петровская д.7", 5, "+7903434543", 1, "2021-07-06", "Сгенерируй тестовые данные (свою учётку и несколько случайных)", List.of("GREY")},
                {"Иванко", "Жадобов", "г.Москва ул.Петровская д.8", 5, "+7903225431", 1, "2021-02-06", "Сгенерируй тестовые данные (свою учётку и несколько случайных)", List.of("BLACK, GREY")}
        };
    }

    @Before
    public void setUp() {
        orderRequest = new OrderRequest();
    }

    @After
    public void cancelOrder() {
        orderRequest.cancel(orderIn);
    }

    @Test // Создание заказа
    @DisplayName("Creating an order with checking the color field")
    public void getTestCaseCreateOrder() {
        OrderFields orderFields = new OrderFields(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = orderRequest.create(orderFields)
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
        orderIn = response.extract().path("track");

        ValidatableResponse responseOrderAll = orderRequest.getOrder(orderIn);
        responseOrderAll.assertThat().body("order.color", equalTo(color));

    }

}
