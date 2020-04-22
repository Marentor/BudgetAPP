package com.example.budgetapp.models;

import java.util.List;

public class TransactionsResponse {
    private List<Transaction> transactions;

    public TransactionsResponse(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

