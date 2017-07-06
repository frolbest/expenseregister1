package com.phpdaddy.expenseregister;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {
    private int amount;
    private Date date;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
