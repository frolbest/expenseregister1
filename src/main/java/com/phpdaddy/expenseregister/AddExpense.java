package com.phpdaddy.expenseregister;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class AddExpense extends JDialog {
    private JPanel contentPane;
    private JTextField amountTextField;
    private JButton addExpenseButton;
    private JButton cancelButton;
    private JXDatePicker datePicker;
    private ExpensesDao expensesDao;

    public AddExpense(ExpensesDao expensesDao) {
        this.expensesDao = expensesDao;
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);

        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addExpenseButton.addActionListener(e -> {
            Expense expense = new Expense();
            try {
                expense.setAmount(Integer.parseInt(amountTextField.getText()));
                if (datePicker.getDate() == null) {
                    throw new NullPointerException("Date should be filled");
                }
                expense.setDate(datePicker.getDate());
            } catch (NumberFormatException | NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Incorrect input format");
                return;
            }
            try {
                expensesDao.save(expense);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "Error writing file");
            }
        });
    }

    private void onCancel() {
        dispose();
    }
}
