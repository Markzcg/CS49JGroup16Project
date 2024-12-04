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

public class AppointmentTest {
    private Appointment onetime;
    private Appointment daily;
    private Appointment monthly;
    private Appointment onetime2;
    private Appointment daily2;
    private Appointment monthly2;
    private Appointment dailyCopy;
    private Appointment monthlyCopy;
    private Appointment onetimeCopy;


    @Before
    public void setup() {
        LocalDate start1 = LocalDate.of(2024, 10, 15);
        LocalDate end1 = LocalDate.of(2024, 10, 20);
        LocalDate start2 = LocalDate.of(2024, 10, 16);
        LocalDate end2= LocalDate.of(2024, 11, 16);
        LocalDate start3 = LocalDate.of(2024, 10, 14);
        LocalDate end3 = LocalDate.of(2024, 12, 14);
        LocalDate start4 = LocalDate.of(2024, 10, 17);

        onetime = new OnetimeAppointment("testOneTime", start1);
        daily = new DailyAppointment("testDaily", start2, end1);
        monthly = new MonthlyAppointment("testMonthly", start3, end3);

        onetime2 = new OnetimeAppointment("testOneTime", start4);
        daily2 = new DailyAppointment("testDaily", start1, end1);
        monthly2 = new DailyAppointment("testMonthly", start2, end2);

        onetimeCopy = new OnetimeAppointment("testOneTime", start1);
        dailyCopy = new DailyAppointment("testDaily", start2, end1);
        monthlyCopy = new MonthlyAppointment("testMonthly", start3, end3);
    }

    // Appointment Subclass Objects Testing
    @Test
    public void testOneTimeAppointment(){
        LocalDate oneTimeStart = onetime.getStartDate();
        LocalDate oneTimeEnd = onetime.getEndDate();
        assertEquals(oneTimeStart, oneTimeEnd);
    }

    @Test
    public void testDailyAppointment(){
        LocalDate startDate = LocalDate.of(2024, 10, 16);
        LocalDate endDate = LocalDate.of(2024, 10, 20);
        LocalDate dailyStart = daily.getStartDate();
        LocalDate dailyEnd = daily.getEndDate();
        assertEquals(startDate, dailyStart);
        assertEquals(endDate, dailyEnd);
    }

    @Test
    public void testMonthlyAppointment(){
        LocalDate startDate = LocalDate.of(2024, 10, 14);
        LocalDate endDate = LocalDate.of(2024, 12, 14);
        LocalDate monthlyStart = monthly.getStartDate();
        LocalDate monthlyEnd = monthly.getEndDate();
        assertEquals(startDate, monthlyStart);
        assertEquals(endDate, monthlyEnd);
    }

    // Test Appointment Methods
    @Test
    public void testBeforeStartDate(){
        LocalDate date = LocalDate.of(2024, 10, 13);
        assertFalse(onetime.occursOn(date));
        assertFalse(daily.occursOn(date));
        assertFalse(monthly.occursOn(date));
    }

    @Test
    public void testInBetween(){
        LocalDate date = LocalDate.of(2024, 10, 17);
        assertTrue(onetime2.occursOn(date));
        assertTrue(daily2.occursOn(date));
        assertTrue(monthly2.occursOn(date));
    }

    @Test
    public void testAfterStartDate(){
        LocalDate date = LocalDate.of(2024, 12, 15);
        assertFalse(onetime.occursOn(date));
        assertFalse(daily.occursOn(date));
        assertFalse(monthly.occursOn(date));
    }

    @Test
    public void testAssertEquals(){
        assertEquals(onetime, onetimeCopy);
        assertEquals(daily, dailyCopy);
        assertEquals(monthly, monthlyCopy);
        assertNotEquals(onetime, onetime2);
        assertNotEquals(daily, daily2);
        assertNotEquals(monthly, monthly2);
    }

    @Test
    public void testCompareTo() {
        LocalDate start1 = LocalDate.of(2024, 10, 22);
        LocalDate start2 = LocalDate.of(2024, 10, 23);
        LocalDate start3 = LocalDate.of(2024, 10, 24);
        LocalDate end1 = LocalDate.of(2024, 10, 25);
        LocalDate end2 = LocalDate.of(2024, 11, 24);
        Appointment a1 = new OnetimeAppointment("a1", start1);
        Appointment a2 = new OnetimeAppointment("a2", start2);
        Appointment a3 = new DailyAppointment("a3", start3, end1);
        Appointment a4 = new DailyAppointment("a4", start3, end2);
        Appointment a5 = new MonthlyAppointment("a5", start3, end2);
        Appointment a6 = new MonthlyAppointment("a6", start3, end2);

        Appointment[] appointments = {a5, a4, a3, a1, a2, a6};
        Appointment[] expected = {a1, a2, a3, a4, a5, a6};

        Arrays.sort(appointments);
        assertArrayEquals(expected, appointments);
    }

}
