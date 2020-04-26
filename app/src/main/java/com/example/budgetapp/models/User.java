package com.example.budgetapp.models;

import java.time.LocalDateTime;
import java.util.Date;

public class User {

    private int id;
    private String created_at;
    private float balance;

    public User(int id, String created_at, float balance) {
        this.id = id;
        this.created_at = created_at;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
