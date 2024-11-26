package obj;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AppointmentManager {
    private HashSet<Appointment> list;

    public AppointmentManager() {
        list = new HashSet<Appointment>();
    }

    public HashSet<Appointment> getList() {
        return list;
    }

    void add(Appointment appointment) {
        try {
            if (!list.add(appointment)) {
                throw new IllegalArgumentException("Appointment already exists!");
            }
        } catch (IllegalArgumentException e1) {
            System.out.println("Error: " + e1.getMessage());
        }
    }

    void delete(Appointment appointment) {
        try {
            if (!list.remove(appointment)) {
                throw new IllegalArgumentException("Appointment does not exist!");
            }
        } catch (IllegalArgumentException e2) {
            System.out.println("Error: " + e2.getMessage());
        }
    }

    void update(Appointment current, Appointment modified) {
        try {
            if (!list.remove(current)) {
                throw new IllegalArgumentException("Appointment does not exist!");
            }
        } catch (IllegalArgumentException e1) {
            System.out.println("Error: " + e1.getMessage());
        }

        try {
            if (!list.add(modified)) {
                throw new IllegalArgumentException("Appointment already exists!");
            }
        } catch (IllegalArgumentException e2) {
            System.out.println("Error: " + e2.getMessage());
        }
    }

    Appointment[] getAppointmentsOn(LocalDate date, Comparator<Appointment> order) {
        PriorityQueue<Appointment> tempList = new PriorityQueue<Appointment>(order);

        if (date == null) {
            for (Appointment appointment : list) {
                tempList.offer(appointment);
            }
        }


        Appointment[] sortedList = tempList.toArray();

        return sortedList;
    }

}
