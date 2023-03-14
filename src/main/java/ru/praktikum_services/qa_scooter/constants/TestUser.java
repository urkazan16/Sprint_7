package ru.praktikum_services.qa_scooter.constants;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUser {

    public static final String LOGIN = "Test_" + RandomStringUtils.randomAlphabetic(10);
    public static final String PASSWORD = "12" + RandomStringUtils.randomAlphabetic(10);
    public static final String FIRST_NAME = "Test_" + RandomStringUtils.randomAlphabetic(10);

}