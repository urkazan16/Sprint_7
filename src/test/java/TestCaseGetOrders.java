import io.qameta.allure.junit4.DisplayName;
import order_model.OrderRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class TestCaseGetOrders {

    private OrderRequest orderRequest;

    @Before
    public void setUp() {
        orderRequest = new OrderRequest();
    }

    @Test // Получить заказ
    @DisplayName("Receive an order")
    public void getTestCaseGetOrder() {
        orderRequest.get()
                .assertThat()
                .body("orders", notNullValue());
    }
}
