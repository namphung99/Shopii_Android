package com.example.shopii.model;

import java.io.Serializable;

public class User implements Serializable {
    public String fullname,  email, password;

    public User(){

    }

    public User(String fullname, String password, String email) {
        this.fullname = fullname;
        this.password = password;
        this.email = email;
    }
}
