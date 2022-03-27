package edu.sprint3;

import lombok.Data;

@Data
public class CourierCredentials {
    private String login;
    private String password;
    private String firstName;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(String login) {
        this.login = login;
    }
}
