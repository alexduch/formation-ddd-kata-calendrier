import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendrierHolidaysTest {

  private final BankHolidaysMock bankHolidays = new BankHolidaysMock(List.of(LocalDate.of(2024, 8, 15)));

  @Test
  void shouldNotAdd() {
    Calendrier calendrier = new Calendrier(bankHolidays);

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.of(2024, 7, 6), LocalTime.of(11, 0), LocalTime.NOON));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAdd2() {
    Calendrier calendrier = new Calendrier(bankHolidays);

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.of(2024, 7, 7), LocalTime.of(11, 0), LocalTime.NOON));

    assertFalse(status.ok);
  }

  @Test
  void shouldNotAdd3() {
    Calendrier calendrier = new Calendrier(bankHolidays);

    SchedulingStatus status = calendrier.add(
        new Evennement(LocalDate.of(2024, 8, 15), LocalTime.of(11, 0), LocalTime.NOON));

    assertFalse(status.ok);
  }


  @Test
  void shouldAdd() {
    Calendrier calendrier = new Calendrier(bankHolidays);

    assertDoesNotThrow(() -> calendrier.add(new Evennement(LocalDate.of(2024, 7, 4), LocalTime.of(11, 0), LocalTime.NOON)));
  }

}