package com.phpdaddy.expenseregister;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesDaoImpl implements ExpensesDao {
    protected final List<Expense> expenses;

    public ExpensesDaoImpl(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        expenses = (List<Expense>) ois.readObject();
        ois.close();
    }

    public ExpensesDaoImpl() {
        expenses = new ArrayList<>();
    }

    /*public static ExpensesDaoImpl getInstance() {
        try {
            return new ExpensesDaoImpl("t.tmp");
        } catch (Exception e) {
            return new ExpensesDaoImpl();
        }
    }*/

    public List<Expense> findAll() {
        return expenses;
    }

    public void save(Expense expense) throws IOException {
        expenses.add(expense);
        FileOutputStream fos = new FileOutputStream("t.tmp");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(expenses);
        oos.close();
    }

    @Override
    public int calcSum(final Date startDate, final Date endDate) {
        return expenses.stream().filter(expense -> (expense.getDate().after(startDate) && expense.getDate().before(endDate)) || expense.getDate().equals(startDate))
                .mapToInt(expense -> expense.getAmount()).sum();
    }

    @Override
    public double calcAverage(Date startDate, Date endDate) {
        return expenses.stream().filter(expense -> (expense.getDate().after(startDate) && expense.getDate().before(endDate)) || expense.getDate().equals(startDate))
                .mapToInt(expense -> expense.getAmount()).average().getAsDouble();
    }

    @Override
    public void cleanAll() throws IOException {
        FileOutputStream fos = new FileOutputStream("t.tmp");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(null);
        oos.close();
    }

}
