import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendarHolidaysTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(List.of(LocalDate.of(2024, 8, 15)));
  private final EventSaver eventSaver = (event) -> {};

  @Test
  void shouldNotAdd() {
    Calendar calendar = new Calendar(bankHolidays);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.of(2024, 7, 6), LocalTime.of(11, 0), LocalTime.NOON), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAdd2() {
    Calendar calendar = new Calendar(bankHolidays);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.of(2024, 7, 7), LocalTime.of(11, 0), LocalTime.NOON), eventSaver);

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAdd3() {
    Calendar calendar = new Calendar(bankHolidays);

    SchedulingStatus status = calendar.add(
        new Event(LocalDate.of(2024, 8, 15), LocalTime.of(11, 0), LocalTime.NOON), eventSaver);

    assertFalse(status.ok);
  }


  @Test
  void shouldAdd() {
    Calendar calendar = new Calendar(bankHolidays);

    SchedulingStatus status =  calendar.add(new Event(LocalDate.of(2024, 7, 4), LocalTime.of(11, 0), LocalTime.NOON), eventSaver);

    assertTrue(status.ok);
  }

}