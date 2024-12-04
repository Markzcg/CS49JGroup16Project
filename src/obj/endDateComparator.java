package obj;

import java.util.Comparator;

public class endDateComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment o1, Appointment o2) {
        return o1.getEndDate().compareTo(o2.getEndDate());
    }
}
