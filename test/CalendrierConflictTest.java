import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class CalendrierConflictTest {

  @Test
  void shouldAddEvent() {
    Calendrier calendrier = new Calendrier();
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0)));

    assertDoesNotThrow(() -> calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0))));
  }

  @Test
  void shouldNotAddEvent() {
    Calendrier calendrier = new Calendrier();
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 10)));

    assertThrows(Exception.class, () -> calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0))));
  }

  @Test
  void shouldNotAddEvent2() {
    Calendrier calendrier = new Calendrier();
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.NOON));

    assertThrows(Exception.class, () -> calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 10))));
  }

  @Test
  void shouldNotAddEvent3() {
    Calendrier calendrier = new Calendrier();
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.NOON));

    assertThrows(Exception.class, () -> calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(11, 30))));
  }

  @Test
  void shouldNotAddEvent4() {
    Calendrier calendrier = new Calendrier();
    calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(11, 30)));

    assertThrows(Exception.class, () -> calendrier.add(new Evennement(LocalDate.now(), LocalTime.of(10, 0), LocalTime.NOON)));
  }
}