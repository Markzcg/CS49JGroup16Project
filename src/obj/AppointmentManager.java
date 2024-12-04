package obj;

import java.time.LocalDate;
import java.util.Arrays;
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

    public void add(Appointment appointment) {
        try {
            if (!list.add(appointment)) {
                throw new IllegalArgumentException("Appointment already exists!");
            }
        } catch (IllegalArgumentException e1) {
            System.out.println("Error: " + e1.getMessage());
            throw e1;
        }
    }

    public void delete(Appointment appointment) {
        try {
            if (!list.remove(appointment)) {
                throw new IllegalArgumentException("Appointment does not exist!");
            }
        } catch (IllegalArgumentException e2) {
            System.out.println("Error: " + e2.getMessage());
            throw e2;
        }
    }

    public void update(Appointment current, Appointment modified) {
        try {
            if (!list.remove(current)) {
                throw new IllegalArgumentException("Appointment does not exist!");
            }
        } catch (IllegalArgumentException e1) {
            System.out.println("Error: " + e1.getMessage());
            throw e1;
        }

        try {
            if (!list.add(modified)) {
                throw new IllegalArgumentException("Appointment already exists!");
            }
        } catch (IllegalArgumentException e2) {
            System.out.println("Error: " + e2.getMessage());
            throw e2;
        }
    }

    public Appointment[] getAppointmentsOn(LocalDate date, Comparator<Appointment> order) {

        PriorityQueue<Appointment> tempList = new PriorityQueue<Appointment>(order);
        Appointment[] sortedList;

        if (date == null) {
            for (Appointment appointment : list) {
                tempList.offer(appointment);
            }
        }
        else {
            for (Appointment appointment : list) {
                if (date.isEqual(appointment.getStartDate())) {
                    tempList.offer(appointment);
                }
            }
        }

        sortedList = new Appointment[tempList.size()];
        tempList.toArray(sortedList);

        Arrays.sort(sortedList, order);

        return sortedList;
    }

}

