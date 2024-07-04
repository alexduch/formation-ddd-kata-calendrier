import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendrierLunchBreakTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(
      List.of(LocalDate.of(2024, 8, 15)));

  @Test
  void shouldNotAddEvent() {
    SchedulingStatus status = new Calendrier(bankHolidays).add(
        new Evennement(LocalDate.now(), LocalTime.NOON, LocalTime.MIDNIGHT));

    assertFalse(status.ok);
  }

  @Test
  void shouldAddEvent() {
    SchedulingStatus status = new Calendrier(bankHolidays).add(
        new Evennement(LocalDate.now(), LocalTime.of(13, 0), LocalTime.MIDNIGHT));

    assertTrue(status.ok);
  }
}