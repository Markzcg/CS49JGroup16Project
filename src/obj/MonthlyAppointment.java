import java.time.LocalDate;

public class MonthlyAppointment extends Appointment {
    public MonthlyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return inBetween(date) && date.getDayOfMonth() == getStartDate().getDayOfMonth();
    }

    @Override
    public String toString() {
        return "Monthly Appointment: " + getDescription() + ", Start Date: " + getStartDate() + ", End Date: " + getEndDate();
    }
}
