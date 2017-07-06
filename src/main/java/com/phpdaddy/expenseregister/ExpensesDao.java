package com.phpdaddy.expenseregister;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ExpensesDao {
    List<Expense> findAll();

    void save(Expense expense) throws IOException;

    int calcSum(Date startDate, Date endDate);

    double calcAverage(Date startDate, Date endDate);

    void cleanAll() throws IOException;
}
