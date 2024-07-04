import java.time.DayOfWeek;
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

  public boolean conflictsWith(Evennement other) {
    return (fin.isAfter(other.debut) && fin.isBefore(other.fin))
        || (debut.isBefore(other.fin) && debut.isAfter(other.debut))
        || (other.debut.isAfter(this.debut) && other.fin.isBefore(this.fin));
  }

  public boolean isOnBankHoliday(BankHolidays bankHolidays) {
    return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || bankHolidays.isBankHoliday(date);
  }
}
