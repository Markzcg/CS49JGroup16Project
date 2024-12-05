package obj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
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
        frame.setSize(1000, 800);
        manager = new AppointmentManager();

        tableModel = new DefaultTableModel(new String[]{"Type", "Description", "Start Date", "End Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 30));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 30));
        table.setRowHeight(35);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 2, 6, 6));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton add = new JButton("New");
        JButton remove = new JButton("Remove");
        JButton viewAll = new JButton("Show All");
        JButton viewDate = new JButton("By Date");
        JButton viewDesc = new JButton("By Description");
        JButton edit = new JButton("Edit");
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(viewAll);
        buttonPanel.add(viewDate);
        buttonPanel.add(viewDesc);
        buttonPanel.add(edit);

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        add.addActionListener(_ -> addAppointment());
        remove.addActionListener(_ -> removeAppointment());
        viewAll.addActionListener(_ -> displayAll());
        viewDate.addActionListener(_ -> sortByDate());
        viewDesc.addActionListener(_ -> sortByDescription());
        edit.addActionListener(_ -> editSelectedAppointment());

        frame.setVisible(true);
    }

    private void addAppointment() {
        JDialog dialog = createEditDialog(null);
        dialog.setVisible(true);
    }

    private void editSelectedAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String description = (String) table.getValueAt(selectedRow, 1);
            LocalDate startDate = LocalDate.parse((String) table.getValueAt(selectedRow, 2));
            LocalDate endDate = LocalDate.parse((String) table.getValueAt(selectedRow, 3));
            Appointment existing = manager.getList().stream()
                    .filter(a -> a.getDescription().equals(description) &&
                            a.getStartDate().equals(startDate) &&
                            a.getEndDate().equals(endDate))
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                JDialog dialog = createEditDialog(existing);
                dialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No appointment selected!");
        }
    }

    private JDialog createEditDialog(Appointment existing) {
        JDialog dialog = new JDialog(frame, existing == null ? "New Appointment" : "Edit Appointment", true);
        dialog.setSize(800, 400);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Onetime", "Daily", "Monthly"});
        if (existing != null) {
            typeComboBox.setSelectedItem(existing.getClass().getSimpleName().replace("Appointment", ""));
            typeComboBox.setEnabled(false);
        }

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField(existing != null ? existing.getDescription() : "");

        JLabel startDateLabel = new JLabel("Start Date:");
        DatePicker startDatePicker = createDatePicker();
        if (existing != null) startDatePicker.setDate(existing.getStartDate());

        JLabel endDateLabel = new JLabel("End Date:");
        DatePicker endDatePicker = createDatePicker();
        if (existing != null) endDatePicker.setDate(existing.getEndDate());

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(typeLabel);
        dialog.add(typeComboBox);
        dialog.add(descriptionLabel);
        dialog.add(descriptionField);
        dialog.add(startDateLabel);
        dialog.add(startDatePicker);
        dialog.add(endDateLabel);
        dialog.add(endDatePicker);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(_ -> {
            String type = (String) typeComboBox.getSelectedItem();
            String description = descriptionField.getText();
            LocalDate startDate = startDatePicker.getDate();
            LocalDate endDate = endDatePicker.getDate();

            if (type.equals("Onetime")) {
                endDate = startDate;
            } else if (endDate == null || endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(dialog, "End date must be after start date!");
                return;
            }

            if (existing != null) {
                manager.delete(existing);
            }

            Appointment newAppointment;
            if (type.equals("Onetime")) {
                newAppointment = new OnetimeAppointment(description, startDate);
            } else if (type.equals("Daily")) {
                newAppointment = new DailyAppointment(description, startDate, endDate);
            } else {
                newAppointment = new MonthlyAppointment(description, startDate, endDate);
            }

            manager.add(newAppointment);
            displayAll();
            dialog.dispose();
        });

        cancelButton.addActionListener(_ -> dialog.dispose());

        return dialog;
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
        manager.getList().stream()
                .sorted(Comparator.comparing(Appointment::getStartDate))
                .forEach(appointment -> tableModel.addRow(new Object[]{
                        appointment.getClass().getSimpleName(),
                        appointment.getDescription(),
                        appointment.getStartDate().toString(),
                        appointment.getEndDate().toString()
                }));
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

    private DatePicker createDatePicker() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setAllowKeyboardEditing(true);
        return new DatePicker(settings);
    }

    public static void main(String[] args) {
        new AppointmentUI();
    }
}
