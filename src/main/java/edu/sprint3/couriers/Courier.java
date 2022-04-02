package edu.sprint3.couriers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class Courier {
    public String login;
    public String password;
    public String firstName;

    public Courier() {
            }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Создаем курьера со случайными данными")
    public static Courier getRandomCourier() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);

        Allure.attachment("Login: ", login);
        Allure.attachment("Password: ", password);
        Allure.attachment("FirstName: ", firstName);

        return new Courier(login, password, firstName);
    }
}
