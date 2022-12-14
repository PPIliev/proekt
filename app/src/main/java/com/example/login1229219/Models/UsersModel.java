package com.example.login1229219.Models;

import androidx.annotation.NonNull;

public class UsersModel {
    private int id;
    private String username;
    private String password;
    private String email;
    private int type;
    private int phone;

    public UsersModel(int id, String username, String password, String email, int type, int phone) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.type = type;
        this.phone = phone;
    }

    public UsersModel(){}

    // ToString method
    @Override
    public String toString() {
        return "UsersModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }

    // getters and setters
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
