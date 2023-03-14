package ru.praktikum_services.qa_scooter.courier;

public class CourierAuthorizationFields {

    private String login;
    private String password;

    public CourierAuthorizationFields(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierAuthorizationFields() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {

        return " Courier { Login: '" + login + "' Password: '" + password + "'}";

    }

}
