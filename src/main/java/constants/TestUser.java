package constants;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUser {

    public static String randomName = RandomStringUtils.randomAlphabetic(10);
    public static final String LOGIN = "Test_" + randomName;
    public static final String PASSWORD = "12" + randomName;
    public static final String FIRST_NAME = "Test_name" + randomName;

}