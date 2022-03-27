package edu.sprint3;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public Date deliveryDate; //"yyyy-MM-dd'
    public String comment;
    public List <String> color;


}
