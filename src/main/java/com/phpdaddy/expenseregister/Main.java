package com.phpdaddy.expenseregister;

import javax.swing.*;
import java.awt.event.*;

public class Main extends JDialog {
    private JPanel contentPane;
    private JButton addExpenseButton;
    private JButton viewExpensesButton;
    private JButton exitButton;
    private ExpensesDao expensesDao;

    public Main() {

        expensesDao = new ExpensesDaoImpl();

        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(exitButton);

        exitButton.addActionListener(e -> onExit());

        // call onExit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        // call onExit() on ESCAPE
        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addExpenseButton.addActionListener(e -> {
            AddExpense addExpense = new AddExpense(expensesDao);
            addExpense.pack();
            addExpense.setLocationRelativeTo(this);
            addExpense.setVisible(true);
        });
        viewExpensesButton.addActionListener(e -> {
            ViewExpenses viewExpenses = new ViewExpenses(expensesDao);
            viewExpenses.pack();
            viewExpenses.setLocationRelativeTo(this);
            viewExpenses.setVisible(true);
        });
    }

    private void onExit() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Main dialog;
        dialog = new Main();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
