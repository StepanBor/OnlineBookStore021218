package com.gmail.stepan1983.DTO;

public class LoginForm {

    private String login;

    private String password;

    public LoginForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginForm() {
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

    @Override
    public String toString() {
        return "LoginForm{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}