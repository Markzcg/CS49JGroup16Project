package tests;
import obj.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;

public class AppointmentManagerTest {
    private AppointmentManager appointments;
    private Appointment onetime;
    private Appointment daily;
    private Appointment weekly;

    @Before
    public void setup(){
        appointments = new AppointmentManager();
        LocalDate start1 = LocalDate.of(2024, 10, 15);
        LocalDate end1 = LocalDate.of(2024, 10, 20);
        LocalDate start2 = LocalDate.of(2024, 10, 17);
        LocalDate end2= LocalDate.of(2024, 11, 5);
        onetime = new OnetimeAppointment("testOneTime", start1);
        daily = new DailyAppointment("testDaily", start2, end1);
        weekly = new MonthlyAppointment("testMonthly", start2, end1);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAdd(){
        appointments.add(onetime);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment already exists");
        appointments.add(onetime);
    }

    @Test
    public void testDelete(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment does not exist");
        appointments.delete(onetime);
    }

    @Test
    public void testUpdateDelete(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment does not exist");
        appointments.update(onetime, daily);
    }

    @Test
    public void testUpdateAdd(){
        appointments.add(onetime);
        appointments.add(daily);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Appointment already exists");
        appointments.update(onetime, daily);
    }

    @Test
    public void testGetAppointmentsOnDate(){
        appointments.add(weekly);
        appointments.add(daily);
        LocalDate testDate = LocalDate.of(2024, 10, 17);

        Appointment[] allAppointments= appointments.getAppointmentsOn(testDate, null);
        Appointment[] expected = {daily, weekly};
        assertArrayEquals(expected, allAppointments);
    }

    @Test
    public void testGetAppointmentsOnComparator(){
        appointments.add(weekly);
        appointments.add(daily);

        Appointment[] allAppointments= appointments.getAppointmentsOn(null, Comparator.comparing(Appointment::getStartDate));
        Appointment[] expected = {weekly, daily};
        assertArrayEquals(expected, allAppointments);
    }

    @Test
    public void testGetAppointmentsOnBoth(){
        appointments.add(weekly);
        appointments.add(daily);
        LocalDate testDate = LocalDate.of(2024, 10, 17);

        Appointment[] allAppointments= appointments.getAppointmentsOn(testDate, Comparator.comparing(Appointment::getStartDate));
        Appointment[] expected = {weekly, daily};
        assertArrayEquals(expected, allAppointments);
    }
}