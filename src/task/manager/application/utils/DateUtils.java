package task.manager.application.utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

	public static boolean checkIsDateToday(LocalDate date) {
		LocalDate today = LocalDate.now();
		return date.isEqual(today);
	}

	public static boolean isValidDuration(String duration) {
		// Regex pattern for "hour:min" format
		String regex = "^(\\d+):(\\d{2})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(duration);
		return matcher.matches();
	}

}
