package obj;

import java.util.Comparator;

public class DescriptionComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment o1, Appointment o2) {
        return o1.getDescription().compareTo(o2.getDescription());
    }
}
