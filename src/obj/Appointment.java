import java.time.LocalDate;

public class Appointment {

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Appointment(String description, LocalDate startDate, LocalDate endDate) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid start or end date.");
        }
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean occursOn(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
    }

    @Override
    public String toString() {
        return "Appointment: " + description + ", Start Date: " + startDate + ", End Date: " + endDate;
    }

    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 20);
        Appointment appointment = new Appointment("School Event", startDate, endDate);

        System.out.println(appointment);

        LocalDate testDate = LocalDate.of(2024, 10, 9);
        System.out.println("Occurs on 2024-10-09: " + appointment.occursOn(testDate));  // false

        testDate = LocalDate.of(2024, 10, 10);
        System.out.println("Occurs on 2024-10-10: " + appointment.occursOn(testDate));  // true

        testDate = LocalDate.of(2024, 10, 15);
        System.out.println("Occurs on 2024-10-15: " + appointment.occursOn(testDate));  // true

        testDate = LocalDate.of(2024, 10, 20);
        System.out.println("Occurs on 2024-10-20: " + appointment.occursOn(testDate));  // true

        testDate = LocalDate.of(2024, 10, 21);
        System.out.println("Occurs on 2024-10-21: " + appointment.occursOn(testDate));  // false
    }
}
