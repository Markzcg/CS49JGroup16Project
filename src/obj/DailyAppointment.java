import java.time.LocalDate;

public class DailyAppointment extends Appointment {
    public DailyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return inBetween(date);
    }

    @Override
    public String toString() {
        return "Daily Appointment: " + getDescription() + ", Start Date: " + getStartDate() + ", End Date: " + getEndDate();
    }
}
