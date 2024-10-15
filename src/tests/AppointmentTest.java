package tests;
import obj.Appointment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;



public class AppointmentTest {
    private Appointment appointment;

    @Before
    public void setup(){
        LocalDate start = LocalDate.of(2024, 10, 10);
        LocalDate end = LocalDate.of(2024, 10, 20);
        appointment = new Appointment("test", start, end);
    }
    @Test
    public void testBeforeStartDate(){
        // < startDate && < endDate = false;
        LocalDate checkDate = LocalDate.of(2024, 9, 10);
        boolean actual = appointment.occursOn(checkDate);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void testOnStartDate(){
        // = startDate && < endDate = true;
        LocalDate checkDate = LocalDate.of(2024, 10, 10);
        boolean actual = appointment.occursOn(checkDate);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testBetweenStartAndEndDate(){
        // > startDate && < endDate = true;
        LocalDate checkDate = LocalDate.of(2024, 10, 15);
        boolean actual = appointment.occursOn(checkDate);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testOnEndDate(){
        // > startDate && = endDate = true;
        LocalDate checkDate = LocalDate.of(2024, 10, 20);
        boolean actual = appointment.occursOn(checkDate);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testAfterEndDate(){
        // > startDate && = endDate = true;
        LocalDate checkDate = LocalDate.of(2024, 10, 21);
        boolean actual = appointment.occursOn(checkDate);
        boolean expected = false;
        assertEquals(expected, actual);
    }



}