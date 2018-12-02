package com.gmail.stepan1983.model;

public enum UserRole {

    ADMIN, CUSTOMER, MANAGER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }

}
