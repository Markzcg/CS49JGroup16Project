import javax.swing.*;
import java.awt.*;

public class AppointmentUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Appointment Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        Color lightBlue = new Color(173, 216, 230);

        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createLineBorder(lightBlue, 3));

        JLabel title = new JLabel("Your Appointments", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        left.add(title, BorderLayout.NORTH);

        String[] columnNames = {"Title", "Date", "Time"};
        Object[][] data = {
            {"Meeting with John", "2024-11-25", "10:00 AM"},
            {"Dentist Appointment", "2024-11-26", "02:00 PM"},
            {"Yoga Class", "2024-11-27", "07:00 PM"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        left.add(scrollPane, BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(0, 1, 5, 5));
        right.setBorder(BorderFactory.createLineBorder(lightBlue, 3));
        JButton add = new JButton("New");
        JButton remove = new JButton("Remove");
        JButton edit = new JButton("Edit");
        JButton viewAll = new JButton("Show All");
        JButton viewDate = new JButton("By Date");
        JButton viewDesc = new JButton("By Description");
        right.add(add);
        right.add(remove);
        right.add(edit);
        right.add(viewAll);
        right.add(viewDate);
        right.add(viewDesc);

        frame.setLayout(new BorderLayout());
        frame.add(left, BorderLayout.CENTER);
        frame.add(right, BorderLayout.EAST);

        frame.setVisible(true);
    }
}
