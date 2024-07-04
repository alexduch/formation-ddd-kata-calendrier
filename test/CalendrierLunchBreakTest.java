import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class CalendrierLunchBreakTest {

  @Test
  void shouldNotAddEvent() {
    assertThrows(Exception.class, () ->  new Calendrier().add(new Evennement(LocalDate.now(), LocalTime.NOON, LocalTime.MIDNIGHT)));
  }

  @Test
  void shouldAddEvent() {
    assertDoesNotThrow(() ->  new Calendrier().add(new Evennement(LocalDate.now(), LocalTime.of(13,0), LocalTime.MIDNIGHT)));
  }
}