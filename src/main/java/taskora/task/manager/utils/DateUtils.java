package taskora.task.manager.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.StringConverter;
import taskora.task.manager.constants.DateConstants;

public class DateUtils {

  private static final DateTimeFormatter formatter = DateTimeFormatter
      .ofPattern(DateConstants.DEFAULT_DATE_FORMAT.toString());

  public static boolean checkIsDateToday(LocalDate date) {
    LocalDate today = LocalDate.now();
    return date.isEqual(today);
  }

  public static String format(LocalDate date) {
    return formatter.format(date);
  }

  public static StringConverter<LocalDate> getConverter() {
    return new javafx.util.StringConverter<LocalDate>() {
      private final DateTimeFormatter dateFormatter = DateTimeFormatter
          .ofPattern(DateConstants.DEFAULT_DATE_FORMAT.toString());

      @Override
      public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
          return LocalDate.parse(string, dateFormatter);
        }
        return null;
      }

      @Override
      public String toString(LocalDate date) {
        if (date != null) {
          return dateFormatter.format(date);
        }
        return "";
      }
    };

  }

//	public static LocalDate format(LocalDate date) {
//		return LocalDate.parse(date, formatter);
//	}

  public static DateTimeFormatter getDefaultFormatter() {
    return formatter;
  }

  public static boolean isValidDuration(String duration) {
    // Regex pattern for "hour:min" format
    String regex = "^(\\d+):(\\d{2})$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(duration);
    return matcher.matches();
  }
}
