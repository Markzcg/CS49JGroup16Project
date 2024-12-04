package tests;
import obj.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.*;

public class AppointmentManagerTest {
    private AppointmentManager appointments;
    private Appointment onetime;
    private Appointment daily;
    private Appointment monthly;
    private Appointment onetime2;
    private Appointment monthly2;
    private Appointment monthly3;
    private Appointment monthly4;

    @Before
    public void setup(){
        appointments = new AppointmentManager();
        LocalDate start1 = LocalDate.of(2024, 10, 15);
        LocalDate end1 = LocalDate.of(2024, 10, 20);
        LocalDate start2 = LocalDate.of(2024, 10, 17);
        LocalDate end2= LocalDate.of(2024, 12, 15);
        LocalDate start3 = LocalDate.of(2024, 10, 31);
        LocalDate end3 = LocalDate.of(2024, 12, 17);
        onetime = new OnetimeAppointment("testOneTime", start1);
        daily = new DailyAppointment("testDaily", start2, end1);
        monthly = new MonthlyAppointment("testMonthly", start3, end3);
        onetime2 = new OnetimeAppointment("testOneTime2", start2);
        monthly2 = new MonthlyAppointment("testMonthly2", start1, end2);
        monthly3 = new MonthlyAppointment("testMonthly3", start2, end3);
        monthly4 = new MonthlyAppointment("testMonthly4", start2, end3);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAdd(){
        appointments.add(onetime);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment already exists!");
        appointments.add(onetime);
    }

    @Test
    public void testDelete(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment does not exist!");
        appointments.delete(onetime);
    }

    @Test
    public void testUpdate1(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment does not exist!");
        appointments.update(onetime, daily);
    }

    @Test
    public void testUpdate2(){
        appointments.add(monthly);
        appointments.add(monthly2);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment already exists!");
        appointments.update(monthly, monthly2);
    }

    @Test
    public void testGetAppointmentsOnBothNull(){
        appointments.add(daily);
        appointments.add(monthly);
        appointments.add(onetime);
        Appointment[] allAppointments= appointments.getAppointmentsOn(null, null);
        Appointment[] expected = {onetime, daily, monthly};
        assertArrayEquals(expected, allAppointments);
    }
    /*
    @Test
    public void testGetAppointmentsOnNullDate(){
        appointments.add(daily);
        appointments.add(monthly3);
        appointments.add(onetime);
        Comparator<Appointment> endDateComparator = new endDateComparator();
        Appointment[] allAppointments= appointments.getAppointmentsOn(null, endDateComparator);
        Appointment[] expected = {onetime, daily, monthly3};
        assertArrayEquals(expected, allAppointments);
    }

    @Test
    public void testGetAppointmentsOnNullComparator(){
        appointments.add(daily);
        appointments.add(monthly3);
        appointments.add(onetime);
        appointments.add(onetime2);
        LocalDate testDate = LocalDate.of(2024, 10, 17);
        Appointment[] allAppointments= appointments.getAppointmentsOn(testDate, null);
        Appointment[] expected = {onetime2, daily, monthly3};
        assertArrayEquals(expected, allAppointments);
    }

    @Test
    public void testGetAppointmentsOn(){
        appointments.add(daily);
        appointments.add(monthly3);
        appointments.add(onetime);
        appointments.add(onetime2);
        appointments.add(monthly4);
        Comparator<Appointment> dateComparator = new descriptionComparator();

        LocalDate testDate = LocalDate.of(2024, 10, 17);
        Appointment[] allAppointments= appointments.getAppointmentsOn(testDate, dateComparator);
        Appointment[] expected = {daily, monthly3, monthly4, onetime2};
        assertArrayEquals(expected, allAppointments);
    } */
}
