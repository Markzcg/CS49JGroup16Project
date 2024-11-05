package obj;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Appointment implements Comparable<Appointment> {
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

    protected boolean inBetween(LocalDate date) {
        return (date.isEqual(startDate) || date.isEqual(endDate) || (date.isAfter(startDate) && date.isBefore(endDate)));
    }

    public abstract boolean occursOn(LocalDate date);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Appointment other = (Appointment) obj;
        return description.equals(other.description) && startDate.equals(other.startDate) && endDate.equals(other.endDate);
    }

    @Override
    public int hashCode() {
        return description.hashCode() + startDate.hashCode() + endDate.hashCode();
    }

    @Override
    public int compareTo(Appointment other) {
        int startComparison = this.startDate.compareTo(other.startDate);
        if (startComparison != 0) return startComparison;
        int endComparison = this.endDate.compareTo(other.endDate);
        if (endComparison != 0) return endComparison;
        return this.description.compareTo(other.description);
    }
}

