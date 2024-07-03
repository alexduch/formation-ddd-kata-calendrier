import java.time.LocalDate;
import java.time.LocalTime;

public class Evennement {

  LocalDate date;
  LocalTime debut;
  LocalTime fin;

  public Evennement(LocalDate date, LocalTime debut, LocalTime fin) {
    this.date = date;
    this.debut = debut;
    this.fin = fin;
  }

}
