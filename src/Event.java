import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

  LocalDate date;
  LocalTime debut;
  LocalTime fin;

  public Event(LocalDate date, LocalTime debut, LocalTime fin) {
    this.date = date;
    this.debut = debut;
    this.fin = fin;
  }

  public boolean conflictsWith(Event other) {
    return (fin.isAfter(other.debut) && fin.isBefore(other.fin))
        || (debut.isBefore(other.fin) && debut.isAfter(other.debut))
        || (other.debut.isAfter(this.debut) && other.fin.isBefore(this.fin));
  }

  public boolean isOnBankHoliday(BankHolidays bankHolidays) {
    return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || bankHolidays.isBankHoliday(date);
  }

  public boolean startAfter(LocalTime localTime) {
    return debut.isAfter(localTime);
  }

  public boolean endsBefore(LocalTime localTime) {
    return fin.isBefore(localTime);
  }

  public boolean startBefore(LocalTime localTime) {
    return debut.isBefore(localTime);
  }

  public boolean endsAfter(LocalTime localTime) {
    return fin.isAfter(localTime);
  }
}
