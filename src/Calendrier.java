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

  public void add(Evennement evennement) {
    if (evennements.stream().anyMatch(evennement::conflictsWith)){
      throw new RuntimeException("Conflict");
    }

    if (evennement.fin.isBefore(LocalTime.of(11, 0)) || evennement.debut.isAfter(LocalTime.of(16, 0))) {
      evennements.add(evennement);
      evennements.sort(Comparator.comparing(e -> e.debut));
      return;
    }

    List<Evennement> all = new ArrayList<>();
    Collections.copy(evennements, all);
    all.add(evennement);
    for (int i = 0; i < all.size() - 1; i++) {
      Evennement current = all.get(i);
      Evennement next = all.get(i+1);
      if (Duration.between(current.fin, next.debut).compareTo(Duration.ofMinutes(90)) < 0) {
        throw new RuntimeException();
      }
    }
    evennements.add(evennement);

  }
}
