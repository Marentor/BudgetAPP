package com.example.budgetapp.models;



public class Transaction {
    private int id,category_id;
    private String description,attachment;
    private double amount;
    private String created_at;


    public Transaction(int id, double amount, int category_id, String description, String attachment, String created_at) {
        this.id = id;
        this.amount = amount;
        this.category_id = category_id;
        this.description = description;
        this.attachment = attachment;
        this.created_at = created_at;
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


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
