import java.time.LocalDate;

public class OnetimeAppointment extends Appointment {
    public OnetimeAppointment(String description, LocalDate date) {
        super(description, date, date);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return date.isEqual(getStartDate());
    }

    @Override
    public String toString() {
        return "Onetime Appointment: " + getDescription() + ", Date: " + getStartDate();
    }
}

