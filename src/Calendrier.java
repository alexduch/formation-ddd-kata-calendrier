import java.time.Duration;
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
    if (evennements.stream().anyMatch(evennement::conflictsWith)){
      return new SchedulingStatus(false,"Conflict");
    }

    if (evennement.fin.isBefore(ONZE_HEURES) || evennement.debut.isAfter(SEIZE_HEURE)) {
      evennements.add(evennement);
      evennements.sort(Comparator.comparing(e -> e.debut));
      return new SchedulingStatus(true, null);
    }

    List<Evennement> all = new ArrayList<>();
    Collections.copy(evennements, all);
    all.add(evennement);
    for (int i = 0; i < all.size() - 1; i++) {
      Evennement current = all.get(i);
      Evennement next = all.get(i+1);
      if (Duration.between(current.fin, next.debut).compareTo(Duration.ofMinutes(90)) < 0) {
        return new SchedulingStatus(false, "Lunch break");
      }
    }
    evennements.add(evennement);
    return new SchedulingStatus(true, null);
  }
}
