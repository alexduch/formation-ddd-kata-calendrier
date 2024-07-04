import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendarLunchBreakTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(
      List.of(LocalDate.of(2024, 8, 15)));
  private final EventSaver eventSaver = (event) -> {};

  @Test
  void shouldAddEvent() {
    SchedulingStatus status = new Calendar(bankHolidays).add(
        new Event(LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(23, 0)), eventSaver);

    assertTrue(status.ok);
  }

  @Test
  void shouldAddEvent2() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0)), eventSaver);


    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.NOON, LocalTime.of(14, 0)), eventSaver);

    assertTrue(status.ok);
  }

  @Test
  void shouldAddEvent3() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(17, 0)), eventSaver);


    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0)), eventSaver);

    assertTrue(status.ok);
  }

  @Test
  void shouldAddEvent4() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(15, 0)), eventSaver);


    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(16, 0)), eventSaver);

    assertTrue(status.ok);
  }

  @Test
  void shouldNotAddEvent() {
    SchedulingStatus status = new Calendar(bankHolidays).add(
        new Event(LocalDate.now(), LocalTime.NOON, LocalTime.of(23, 0)), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent2() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0)), eventSaver);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(15, 0)), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent3() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(17, 0)), eventSaver);


    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 30)), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent4() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 29)), eventSaver);


    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(14, 30), LocalTime.of(16, 0)), eventSaver);

    assertFalse(status.ok);
  }
}