package com.example.password_storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt")
    private String salt;

    public User() {
        this.username = "username";
        this.password = "password";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() { return this.salt; }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }
}
