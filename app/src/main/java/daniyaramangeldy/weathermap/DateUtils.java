package daniyaramangeldy.weathermap;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by daniyaramangeldy on 21.07.17.
 */

public class DateUtils {

    public static String setTime(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        String date = String.format("%s %s %s",
                calendar.get(Calendar.DAY_OF_MONTH),
                getMonthName(calendar.get(Calendar.MONTH)),
                calendar.get(Calendar.YEAR)
        );
        return date;
    }

    private static String getMonthName(int month) {
        String monthName = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        monthName = dfs.getMonths()[month];
        return monthName;
    }
}
