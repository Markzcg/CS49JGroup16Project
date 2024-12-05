package obj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Comparator;

public class AppointmentUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private AppointmentManager manager;

    public AppointmentUI() {
        frame = new JFrame("Appointment Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        manager = new AppointmentManager();
        String[] columnNames = {"Type", "Description", "Start Date", "End Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton add = new JButton("New");
        JButton remove = new JButton("Remove");
        JButton viewAll = new JButton("Show All");
        JButton viewDate = new JButton("By Date");
        JButton viewDesc = new JButton("By Description");
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(viewAll);
        buttonPanel.add(viewDate);
        buttonPanel.add(viewDesc);
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        add.addActionListener(_ -> addAppointment());
        remove.addActionListener(_ -> removeAppointment());
        viewAll.addActionListener(_ -> displayAll());
        viewDate.addActionListener(_ -> sortByDate());
        viewDesc.addActionListener(_ -> sortByDescription());
        frame.setVisible(true);
    }

    private void addAppointment() {
        String[] options = {"Onetime", "Daily", "Monthly"};
        String type = (String) JOptionPane.showInputDialog(frame, "Select Appointment Type:", "New Appointment",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (type != null) {
            String description = JOptionPane.showInputDialog(frame, "Enter Description:");
            String startDateStr = JOptionPane.showInputDialog(frame, "Enter Start Date (YYYY-MM-DD):");
            LocalDate startDate = LocalDate.parse(startDateStr);
            Appointment appointment;
            if (type.equals("Onetime")) {
                appointment = new OnetimeAppointment(description, startDate);
            } else {
                String endDateStr = JOptionPane.showInputDialog(frame, "Enter End Date (YYYY-MM-DD):");
                LocalDate endDate = LocalDate.parse(endDateStr);
                if (type.equals("Daily")) {
                    appointment = new DailyAppointment(description, startDate, endDate);
                } else {
                    appointment = new MonthlyAppointment(description, startDate, endDate);
                }
            }
            manager.add(appointment);
            displayAll();
        }
    }

    private void removeAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String description = (String) table.getValueAt(selectedRow, 1);
            LocalDate startDate = LocalDate.parse((String) table.getValueAt(selectedRow, 2));
            LocalDate endDate = LocalDate.parse((String) table.getValueAt(selectedRow, 3));
            Appointment toRemove = manager.getList().stream()
                    .filter(a -> a.getDescription().equals(description) &&
                            a.getStartDate().equals(startDate) &&
                            a.getEndDate().equals(endDate))
                    .findFirst()
                    .orElse(null);
            if (toRemove != null) {
                manager.delete(toRemove);
                displayAll();
            } else {
                JOptionPane.showMessageDialog(frame, "Appointment not found!");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No appointment selected!");
        }
    }

    private void displayAll() {
        tableModel.setRowCount(0);
        for (Appointment appointment : manager.getList()) {
            tableModel.addRow(new Object[]{
                    appointment.getClass().getSimpleName(),
                    appointment.getDescription(),
                    appointment.getStartDate().toString(),
                    appointment.getEndDate().toString()
            });
        }
    }

    private void sortByDate() {
        Appointment[] sorted = manager.getAppointmentsOn(null, Comparator.comparing(Appointment::getStartDate));
        updateTable(sorted);
    }

    private void sortByDescription() {
        Appointment[] sorted = manager.getAppointmentsOn(null, Comparator.comparing(Appointment::getDescription));
        updateTable(sorted);
    }

    private void updateTable(Appointment[] sortedAppointments) {
        tableModel.setRowCount(0);
        for (Appointment appointment : sortedAppointments) {
            tableModel.addRow(new Object[]{
                    appointment.getClass().getSimpleName(),
                    appointment.getDescription(),
                    appointment.getStartDate().toString(),
                    appointment.getEndDate().toString()
            });
        }
    }

    public static void main(String[] args) {
        new AppointmentUI();
    }
}
