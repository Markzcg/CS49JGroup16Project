package obj;

import java.time.LocalDate;

public class Appointment {
    private LocalDate startDate;
    private LocalDate endDate;

    public Appointment(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean occursOn(LocalDate date) {
        return (date.isEqual(startDate) || date.isEqual(endDate) || 
               (date.isAfter(startDate) && date.isBefore(endDate)));
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
