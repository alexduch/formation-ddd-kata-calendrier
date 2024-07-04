import java.time.LocalDate;
import java.util.List;

class BankHolidaysMock implements BankHolidays {

  private final List<LocalDate> holidays;

  BankHolidaysMock(List<LocalDate> holidays) {
    this.holidays = holidays;
  }


  @Override
  public boolean isBankHoliday(LocalDate date) {
    return holidays.contains(date);
  }
}
