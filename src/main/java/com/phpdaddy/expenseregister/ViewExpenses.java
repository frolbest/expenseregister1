package com.phpdaddy.expenseregister;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ViewExpenses extends JDialog {
    private JPanel contentPane;
    private JButton cancelButton;
    private JTable expensesAllTable;
    private JTabbedPane tabbedPane1;
    private JButton calculateButton;
    private JComboBox comboBoxType;
    private JComboBox comboBoxDate;
    private JComboBox comboBoxMonth;
    private JXDatePicker datePicker;
    private JLabel resultLabel;
    private JButton cleanAllButton;
    private ExpensesDao expensesDao;

    public ViewExpenses(ExpensesDao expensesDao) {
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

        expensesAllTable.setModel(buildTableModel(expensesDao.findAll()));
        calculateButton.addActionListener(e -> {
            String type = comboBoxType.getSelectedItem().toString();
            String date = comboBoxDate.getSelectedItem().toString();
            Date startDate = null;
            Date endDate = null;
            if (date.toLowerCase().equals("day")) {
                startDate = datePicker.getDate();
                if (startDate == null) {
                    JOptionPane.showMessageDialog(this, "Incorrect date format");
                }
                Calendar c = Calendar.getInstance();
                c.setTime(startDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                endDate = c.getTime();
            } else if (date.toLowerCase().equals("month")) {
                GregorianCalendar gcStart = new GregorianCalendar(2017, comboBoxMonth.getSelectedIndex(), 1);

                startDate = new Date(gcStart.getTime().getTime());
                GregorianCalendar gcEnd = new GregorianCalendar(2017, comboBoxMonth.getSelectedIndex(), gcStart.getActualMaximum(Calendar.DAY_OF_MONTH));
                gcEnd.add(Calendar.DAY_OF_MONTH, 1);
                endDate = new Date(gcEnd.getTime().getTime());
            }


            if (type.toLowerCase().equals("sum")) {
                resultLabel.setText(String.valueOf(expensesDao.calcSum(startDate, endDate)) + "$");
            } else if (type.toLowerCase().equals("average")) {
                resultLabel.setText(String.valueOf(expensesDao.calcAverage(startDate, endDate)) + "$");
            }
        });
        comboBoxDate.addActionListener(e -> {
            String date = comboBoxDate.getSelectedItem().toString();
            if (date.toLowerCase().equals("day")) {
                comboBoxMonth.setVisible(false);
                datePicker.setVisible(true);
            } else if (date.toLowerCase().equals("month")) {
                comboBoxMonth.setVisible(true);
                datePicker.setVisible(false);
            }
        });
        cleanAllButton.addActionListener(e -> {
            try {
                expensesDao.cleanAll();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "Error writing file");
            }
        });
    }

    public static DefaultTableModel buildTableModel(List<Expense> rs) {
        // names of columns
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Amount");
        columnNames.add("Date");

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        for (Expense expense : rs) {
            Vector<Object> vector = new Vector<>();
            vector.add(expense.getAmount());
            vector.add(expense.getDate());
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }


    private void onCancel() {
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
