package com.customerservice.entities;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.hibernate.annotations.NaturalId;

@Data
@Log4j
public class Customer {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    public Customer(Integer id, String firstName, String lastName, String email, String password, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
