package obj;

import java.time.LocalDate;
public class Appointment {
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Appointment(String description, LocalDate startDate, LocalDate endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public boolean occursOn(LocalDate date) {
        return (date.isEqual(startDate) || date.isEqual(endDate) ||
                (date.isAfter(startDate) && date.isBefore(endDate)));
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
}