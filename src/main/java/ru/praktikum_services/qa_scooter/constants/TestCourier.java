package ru.praktikum_services.qa_scooter.constants;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum_services.qa_scooter.courier.CourierRegistrationFields;

public class TestCourier {

    public static CourierRegistrationFields getRandom() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierRegistrationFields(login, password, firstName);

    }

}

