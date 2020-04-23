package com.example.budgetapp.models;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionRequest {
    private int category_id;
    private String description,attachment;
    private double amount;

    public TransactionRequest(  String description ) {

        this.description = description;

    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
            try
            {
                this.amount= Double.parseDouble(amount);
                // it means it is double
            } catch (Exception e1) {
                // this means it is not double
                e1.printStackTrace();
            }
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
