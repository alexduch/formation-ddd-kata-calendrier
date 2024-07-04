import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendarConflictTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(
      List.of(LocalDate.of(2024, 8, 15)));
  private final EventSaver eventSaver = (event) -> {};

  @Test
  void shouldAddEvent() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0)), eventSaver);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0)), eventSaver);

    assertTrue(status.ok);
  }

  @Test
  void shouldNotAddEvent() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 10)), eventSaver);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0)), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent2() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.NOON), eventSaver);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 10)), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent3() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(10, 0), LocalTime.NOON), eventSaver);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(11, 30)), eventSaver);
    assertFalse(status.ok);
  }

  @Test
  void shouldNotAddEvent4() {
    Calendar calendar = new Calendar(bankHolidays);
    calendar.add(new Event(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(11, 30)), eventSaver);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.now(), LocalTime.of(10, 0), LocalTime.NOON), eventSaver);
    assertFalse(status.ok);
  }
}