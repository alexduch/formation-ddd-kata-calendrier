import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendrierConflictTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(
      List.of(LocalDate.of(2024, 8, 15)));

  @Test
  void shouldAddEvent() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0)));

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0)));

    assertTrue(status.ok);
  }

  @Test
  void shouldNotAddEvent() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 10)));

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0)));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent2() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.NOON));

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 10)));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent3() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.NOON));

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(11, 30)));
    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent4() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(11, 30)));

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.NOON));
    assertFalse(status.ok);
  }
}