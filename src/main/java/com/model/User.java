package com.model;

public class User {

    private String name;
    private String Email;

    public User(String name, String email) {
        this.name = name;
        this.Email = email;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", Email=" + Email + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}