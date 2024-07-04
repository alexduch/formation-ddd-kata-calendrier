import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Calendar {

  public static final LocalTime ONZE_HEURES = LocalTime.of(11, 0);
  public static final LocalTime SEIZE_HEURE = LocalTime.of(16, 0);
  IdEmploye idEmploye;
  List<Event> events = new ArrayList<>();
  BankHolidays bankHolidays;

  public Calendar(BankHolidays bankHolidays) {
    this.bankHolidays = bankHolidays;
  }

  public SchedulingStatus add(Event event, EventSaver eventSaver) {
    if (event.isOnBankHoliday(bankHolidays)) {
      return new SchedulingStatus(false, "Holiday");
    }
    if (events.stream().anyMatch(event::conflictsWith)) {
      return new SchedulingStatus(false, "Conflict");
    }

    if (event.fin.isBefore(ONZE_HEURES) || event.debut.isAfter(SEIZE_HEURE)) {
      addEvent(event, eventSaver);
      return new SchedulingStatus(true, null);
    }

    if( events.isEmpty()) {
      if (event.startAfter(ONZE_HEURES.plusMinutes(90)) || event.endsBefore(SEIZE_HEURE.minusMinutes(90))) {
        addEvent(event, eventSaver);
        return new SchedulingStatus(true, null);
      }
      return new SchedulingStatus(false, "Lunch break");
    }

    Event other = new Event(LocalDate.now(), ONZE_HEURES, SEIZE_HEURE);
    List<Event> all = new ArrayList<>(events.stream().filter(e -> e.conflictsWith(other)).toList());
    all.add(event);
    all.sort(Comparator.comparing(e -> e.debut));
    if (all.get(0).startAfter(ONZE_HEURES.plusMinutes(90))) {
      addEvent(event, eventSaver);
      return new SchedulingStatus(true, null);
    }
    if (all.get(all.size() - 1).endsBefore(SEIZE_HEURE.minusMinutes(90))) {
      addEvent(event, eventSaver);
      return new SchedulingStatus(true, null);
    }
    for (int i = 0; i < all.size() - 1; i++) {
      Event current = all.get(i);
      Event next = all.get(i + 1);
      if (Duration.between(current.fin, next.debut).compareTo(Duration.ofMinutes(90)) >= 0) {
        addEvent(event, eventSaver);
        return new SchedulingStatus(true, null);
      }
    }

    return new SchedulingStatus(false, "Lunch break");
  }

  private void addEvent(Event event, EventSaver eventSaver) {
    events.add(event);
    events.sort(Comparator.comparing(e -> e.debut));
    eventSaver.save(event);
  }
}
