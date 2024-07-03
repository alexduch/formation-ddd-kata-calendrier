import java.time.LocalTime;
import java.util.List;

public class Calendrier {

  IdEmploye idEmploye;
  List<Evennement> evennements;

  public void add(Evennement evennement) {
    if (evennement.fin.isBefore(LocalTime.of(11, 0)) || evennement.debut.isAfter(LocalTime.of(16, 0))) {
      evennements.add(evennement);
    }

  }
}
