package com.sinhvien.anhemtoicodedienthoai.model;

import java.io.Serializable;

public class Accounts implements Serializable {
    String username;
    String password;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Accounts(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public Accounts() {
    }
}
