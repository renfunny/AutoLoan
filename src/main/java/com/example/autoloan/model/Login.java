package com.example.autoloan.model;

public class Login {
    private String username;
    private String password;

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "12345";

    public boolean validate(){
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
