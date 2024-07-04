import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendrierLunchBreakTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(
      List.of(LocalDate.of(2024, 8, 15)));

  @Test
  void shouldAddEvent() {
    SchedulingStatus status = new Calendrier(bankHolidays).add(
        new Evennement(LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(23, 0)));

    assertTrue(status.ok);
  }

  @Test
  void shouldAddEvent2() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0)));


    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.NOON, LocalTime.of(14, 0)));

    assertTrue(status.ok);
  }

  @Test
  void shouldAddEvent3() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(17, 0)));


    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0)));

    assertTrue(status.ok);
  }

  @Test
  void shouldAddEvent4() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(15, 0)));


    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(16, 0)));

    assertTrue(status.ok);
  }

  @Test
  void shouldNotAddEvent() {
    SchedulingStatus status = new Calendrier(bankHolidays).add(
        new Evennement(LocalDate.now(), LocalTime.NOON, LocalTime.of(23, 0)));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent2() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0)));

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(15, 0)));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent3() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(17, 0)));


    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 30)));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent4() {
    Calendrier calendrier = new Calendrier(bankHolidays);
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 29)));


    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.now(), LocalTime.of(14, 30), LocalTime.of(16, 0)));

    assertFalse(status.ok);
  }
}