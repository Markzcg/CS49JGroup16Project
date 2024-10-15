import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Appointment appointment = new Appointment(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 20));

        LocalDate testDate1 = LocalDate.of(2024, 10, 9);
        LocalDate testDate2 = LocalDate.of(2024, 10, 10);
        LocalDate testDate3 = LocalDate.of(2024, 10, 15);
        LocalDate testDate4 = LocalDate.of(2024, 10, 20);
        LocalDate testDate5 = LocalDate.of(2024, 10, 21);

        System.out.println("Test 1 (Before Start Date): " + appointment.occursOn(testDate1));
        System.out.println("Test 2 (On Start Date): " + appointment.occursOn(testDate2));
        System.out.println("Test 3 (Between Start and End Date): " + appointment.occursOn(testDate3));
        System.out.println("Test 4 (On End Date): " + appointment.occursOn(testDate4));
        System.out.println("Test 5 (After End Date): " + appointment.occursOn(testDate5));
    }
}
