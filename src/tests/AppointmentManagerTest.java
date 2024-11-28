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
    private Appointment monthly;
    private Appointment onetime2;
    private Appointment monthly2;
    private Appointment monthly3;

    @Before
    public void setup(){
        appointments = new AppointmentManager();
        LocalDate start1 = LocalDate.of(2024, 10, 15);
        LocalDate end1 = LocalDate.of(2024, 10, 20);
        LocalDate start2 = LocalDate.of(2024, 10, 17);
        LocalDate end2= LocalDate.of(2024, 10, 21);
        LocalDate start3 = LocalDate.of(2024, 10, 31);
        LocalDate end3 = LocalDate.of(2024, 12, 17);
        onetime = new OnetimeAppointment("testOneTime", start1);
        daily = new DailyAppointment("testDaily", start2, end1);
        monthly = new MonthlyAppointment("testMonthly", start3, end3);
        onetime2 = new OnetimeAppointment("testOneTime2", start2);
        monthly2 = new MonthlyAppointment("TestMonthly2", start1, end2);
        monthly3 = new MonthlyAppointment("TestMonthly2", start2, end3);
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
    public void testGetAppointmentsOnNullDate(){
        appointments.add(daily);
        appointments.add(monthly3);
        appointments.add(onetime);
        // Date is null so return all appointments by order of insertion
        Comparator<Appointment> dateComparator = Comparator.comparing(Appointment::getStartDate);
        Appointment[] allAppointments= appointments.getAppointmentsOn(null, dateComparator);
        Appointment[] expected = {onetime, monthly3, daily};
        assertArrayEquals(expected, allAppointments);
    }

    @Test
    public void testGetAppointmentsOnNullComparator(){
        appointments.add(daily); // 10/17/24 - 10/20/24
        appointments.add(monthly3); // 10/17/24 - 12/17/24
        appointments.add(onetime); // 10/15/24
        appointments.add(onetime2); // 10/17/24
        LocalDate testDate = LocalDate.of(2024, 10, 17);
        // expected only dates on 10/17/24 and sorted by natural order
        Appointment[] allAppointments= appointments.getAppointmentsOn(testDate, null);
        Appointment[] expected = {onetime2, monthly3, daily};
        assertArrayEquals(expected, allAppointments);
    }

    @Test
    public void testGetAppointmentsOn(){
        appointments.add(daily); // 10/17/24 - 10/20/24
        appointments.add(monthly3); // 10/17/24 - 12/17/24
        appointments.add(onetime); // 10/15/24
        appointments.add(onetime2);
        Comparator<Appointment> dateComparator = Comparator.comparing(Appointment::getEndDate);

        LocalDate testDate = LocalDate.of(2024, 10, 17);
        Appointment[] allAppointments= appointments.getAppointmentsOn(testDate, dateComparator);
        Appointment[] expected = {onetime2, daily, monthly3};
        assertArrayEquals(expected, allAppointments);
    }
}
