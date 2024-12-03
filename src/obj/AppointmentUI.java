import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AppointmentUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Appointment> appointments;

    public AppointmentUI() {
        frame = new JFrame("Appointment Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        appointments = new ArrayList<>();
        String[] columnNames = {"Title", "Date", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 3));
        left.add(new JLabel("Your Appointments", SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton add = new JButton("New");
        JButton remove = new JButton("Remove");
        JButton edit = new JButton("Edit");
        JButton viewAll = new JButton("Show All");
        JButton viewDate = new JButton("By Date");
        JButton viewDesc = new JButton("By Description");
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(edit);
        buttonPanel.add(viewAll);
        buttonPanel.add(viewDate);
        buttonPanel.add(viewDesc);

        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.add(buttonPanel, BorderLayout.WEST);
        leftContent.add(scrollPane, BorderLayout.CENTER);

        frame.add(leftContent, BorderLayout.CENTER);
        frame.setVisible(true);

        add.addActionListener(e -> addAppointment());
        remove.addActionListener(e -> removeAppointment());
        edit.addActionListener(e -> editAppointment());
        viewAll.addActionListener(e -> displayAll());
        viewDate.addActionListener(e -> sortByDate());
        viewDesc.addActionListener(e -> sortByDescription());
    }

    private void addAppointment() {
        String title = JOptionPane.showInputDialog(frame, "Enter Title:");
        String date = JOptionPane.showInputDialog(frame, "Enter Date (YYYY-MM-DD):");
        String time = JOptionPane.showInputDialog(frame, "Enter Time (HH:MM AM/PM):");

        if (title != null && date != null && time != null) {
            appointments.add(new Appointment(title, date, time));
            tableModel.addRow(new Object[]{title, date, time});
        }
    }

    private void removeAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            appointments.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(frame, "No appointment selected!");
        }
    }

    private void editAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Appointment appointment = appointments.get(selectedRow);
            String title = JOptionPane.showInputDialog(frame, "Enter Title:", appointment.getTitle());
            String date = JOptionPane.showInputDialog(frame, "Enter Date (YYYY-MM-DD):", appointment.getDate());
            String time = JOptionPane.showInputDialog(frame, "Enter Time (HH:MM AM/PM):", appointment.getTime());

            if (title != null && date != null && time != null) {
                appointment.setTitle(title);
                appointment.setDate(date);
                appointment.setTime(time);
                tableModel.setValueAt(title, selectedRow, 0);
                tableModel.setValueAt(date, selectedRow, 1);
                tableModel.setValueAt(time, selectedRow, 2);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No appointment selected!");
        }
    }

    private void displayAll() {
        tableModel.setRowCount(0);
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{appointment.getTitle(), appointment.getDate(), appointment.getTime()});
        }
    }

    private void sortByDate() {
        appointments.sort(Comparator.comparing(Appointment::getDate));
        displayAll();
    }

    private void sortByDescription() {
        appointments.sort(Comparator.comparing(Appointment::getTitle));
        displayAll();
    }

    public static void main(String[] args) {
        new AppointmentUI();
    }
}

class Appointment {
    private String title;
    private String date;
    private String time;

    public Appointment(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
