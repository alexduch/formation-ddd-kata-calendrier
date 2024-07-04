import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Calendrier {

  public static final LocalTime ONZE_HEURES = LocalTime.of(11, 0);
  public static final LocalTime SEIZE_HEURE = LocalTime.of(16, 0);
  IdEmploye idEmploye;
  List<Evennement> evennements = new ArrayList<>();
  BankHolidays bankHolidays;

  public Calendrier(BankHolidays bankHolidays) {
    this.bankHolidays = bankHolidays;
  }

  public SchedulingStatus add(Evennement evennement) {
    if (evennement.isOnBankHoliday(bankHolidays)) {
      return new SchedulingStatus(false, "Holiday");
    }
    if (evennements.stream().anyMatch(evennement::conflictsWith)) {
      return new SchedulingStatus(false, "Conflict");
    }

    if (evennement.fin.isBefore(ONZE_HEURES) || evennement.debut.isAfter(SEIZE_HEURE)) {
      addEvent(evennement);
      return new SchedulingStatus(true, null);
    }

    if( evennements.isEmpty()) {
      if (evennement.startAfter(ONZE_HEURES.plusMinutes(90)) || evennement.endsBefore(SEIZE_HEURE.minusMinutes(90))) {
        addEvent(evennement);
        return new SchedulingStatus(true, null);
      }
      return new SchedulingStatus(false, "Lunch break");
    }

    Evennement other = new Evennement(LocalDate.now(), ONZE_HEURES, SEIZE_HEURE);
    List<Evennement> all = new ArrayList<>(evennements.stream().filter(e -> e.conflictsWith(other)).toList());
    all.add(evennement);
    all.sort(Comparator.comparing(e -> e.debut));
    if (all.get(0).startAfter(ONZE_HEURES.plusMinutes(90))) {
      addEvent(evennement);
      return new SchedulingStatus(true, null);
    }
    if (all.get(all.size() - 1).endsBefore(SEIZE_HEURE.minusMinutes(90))) {
      addEvent(evennement);
      return new SchedulingStatus(true, null);
    }
    for (int i = 0; i < all.size() - 1; i++) {
      Evennement current = all.get(i);
      Evennement next = all.get(i + 1);
      if (Duration.between(current.fin, next.debut).compareTo(Duration.ofMinutes(90)) >= 0) {
        addEvent(evennement);
        return new SchedulingStatus(true, null);
      }
    }

    return new SchedulingStatus(false, "Lunch break");
  }

  private void addEvent(Evennement evennement) {
    evennements.add(evennement);
    evennements.sort(Comparator.comparing(e -> e.debut));
  }
}
