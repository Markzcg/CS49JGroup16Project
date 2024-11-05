package tests;
import obj.Appointment;
import obj.DailyAppointment;
import obj.MonthlyAppointment;
import obj.OnetimeAppointment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SubClassAppointmentTest {
    private Appointment onetime;
    private Appointment daily;
    private Appointment weekly;
    private Appointment onetime2;
    private Appointment daily2;
    private Appointment weekly2;


    @Before
    public void setup() {
        LocalDate start1 = LocalDate.of(2024, 10, 15);
        LocalDate end1 = LocalDate.of(2024, 10, 20);
        LocalDate start2 = LocalDate.of(2024, 10, 17);
        LocalDate end2= LocalDate.of(2024, 11, 5);
        onetime = new OnetimeAppointment("testOneTime", start1);
        daily = new DailyAppointment("testDaily", start2, end1);
        weekly = new MonthlyAppointment("testMonthly", start2, end1);

        onetime2 = new OnetimeAppointment("testOneTime", start1);
        daily2 = new DailyAppointment("testDaily", start2, end2);
        weekly2 = new MonthlyAppointment("testMonthly", start1, end2);
    }

    @Test
    public void testOneTimeAppointment(){
        LocalDate oneTimeStart = onetime.getEndDate();
        LocalDate oneTimeEnd = onetime.getStartDate();
        assertEquals(oneTimeStart, oneTimeEnd);
    }

    @Test
    public void testBeforeStartDate(){
        LocalDate date = LocalDate.of(2024, 10, 9);
        assertFalse(onetime.occursOn(date));
        assertFalse(daily.occursOn(date));
        assertFalse(weekly.occursOn(date));
    }

    @Test
    public void testInBetween(){
        LocalDate date = LocalDate.of(2024, 10, 15);
        assertTrue(onetime.occursOn(date));
        assertFalse(daily.occursOn(date));
        assertFalse(weekly.occursOn(date));
    }

    @Test
    public void assertEqualsTest(){
        assertEquals(onetime, onetime2);
        assertNotEquals(daily, daily2);
        assertNotEquals(weekly, weekly2);
    }

    @Test
    public void testCompare() {
        LocalDate start1 = LocalDate.of(2024, 10, 22);
        LocalDate start2 = LocalDate.of(2024, 10, 28);
        LocalDate end1 = LocalDate.of(2024, 10, 23);
        LocalDate end2 = LocalDate.of(2024, 10, 31);
        Appointment a1 = new OnetimeAppointment("a1", start1);
        Appointment a2 = new OnetimeAppointment("a2", start1);
        Appointment a3 = new MonthlyAppointment("a3", start1, end1);
        Appointment a4 = new DailyAppointment("a4", start2, end2);
        Appointment a5 = new MonthlyAppointment("a5", start2, end2);

        Appointment[] appointments = {a5, a4, a3, a1, a2};
        Appointment[] expected = {a1, a2, a3, a4, a5};

        Arrays.sort(appointments);
        assertArrayEquals(expected, appointments);
    }

}
