package com.example.budgetapp.models;

import java.time.LocalDateTime;
import java.util.Date;

public class User {

    private  int id;
    private String created_at;
    private double Balance;

    public User(int id, String created_at, double balance) {
        this.id = id;
        this.created_at = created_at;
        Balance = balance;
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

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }
}
