package courer_model;

public class CourierRegistrationFields {
    private String login;
    private String password;
    private String firstName;

    public CourierRegistrationFields(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierRegistrationFields() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {

        return " Courier { Login: '" + login + "' Password: '" + password + "' FirstName: '" + firstName + "'}";

    }
}
