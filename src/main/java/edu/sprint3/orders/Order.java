package edu.sprint3.orders;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Data
@AllArgsConstructor
public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;

    public Order(List<String> color) {
        this.firstName = "Иван";
        this.lastName = "Иванов";
        this.address = "ул. Ленина, 1";
        this.metroStation = "6";
        this.phone = "+79998887766";
        this.rentTime = 2;
        this.deliveryDate = LocalDateTime.now().toString();
        this.comment = "произвольный коммент";
        this.color = color;
    }

    public Order(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment) {
    }
    @Step("Создаем заказ со случайными данными")
    public static Order getRandomOrder() {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10) + " ,"
                + RandomStringUtils.randomAlphanumeric(2)
                + RandomStringUtils.randomAlphanumeric(3);
        final String metroStation = RandomStringUtils.randomNumeric(1);
        final String phone = "+7" + RandomStringUtils.randomNumeric(10);
        final String rentTime = RandomStringUtils.randomNumeric(1);
        final String deliveryDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault()).format(Instant.now());
        final String comment = RandomStringUtils.randomAlphabetic(15);

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }
}

