package com.phpdaddy.expenseregister.test;

import com.phpdaddy.expenseregister.Expense;
import com.phpdaddy.expenseregister.ExpensesDao;
import com.phpdaddy.expenseregister.ExpensesDaoImpl;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

public class ExpensesTest {


    @Test
    public void testSum() throws Exception {
        ArrayList<Expense> expenses = this.populateExpenses();
        ExpensesDaoImpl expensesDao = new ExpensesDaoImpl(getTempFile());
        Date startDate = this.mockDate("January 2, 2010");
        Date endDate = this.mockDate("January 4, 2010");

        assertEquals(expensesDao.calcSum(startDate, endDate), 200);
    }


    @Test
    public void testAverage() throws Exception {
        ArrayList<Expense> expenses = this.populateExpenses();
        ExpensesDaoImpl expensesDao = new ExpensesDaoImpl(getTempFile());

        Date startDate = this.mockDate("January 2, 2010");
        Date endDate = this.mockDate("January 4, 2010");

        assertEquals(expensesDao.calcAverage(startDate, endDate), 100.0);
    }

    private ArrayList<Expense> populateExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(100, this.mockDate("January 2, 2010")));
        expenses.add(new Expense(100, this.mockDate("January 3, 2010")));
        expenses.add(new Expense(100, this.mockDate("January 4, 2010")));
        return expenses;
    }



    public void testSaveAndLoad() throws Exception {
        ExpensesDao expensesDao = new ExpensesDaoImpl(getTempFile());
        expensesDao.cleanAll();
        expensesDao.add(new E)


        ExpensesDao expensesDao2 = new ExpensesDaoImpl(getTempFile());
        // je totam?

        ExpensesDao expensesDao3 = new ExpensesDaoImpl(getTempFile());

    }

    private Date mockDate(String string) {
        try {
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            return format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTempFile() throws IOException {
        return File.createTempFile("expense", ".tmp").getAbsolutePath();
    }

}
