package info.diwe.fitnessapp.utils;

import java.time.LocalDate;

public class DateUtil {

    public static String getLocalDateAsString() {
        int digits = 2;
        String format = String.format("%%0%dd", digits);

        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());

        String month = String.valueOf(now.getMonthValue());
        month = String.format(format, Long.parseLong(month));

        String day = String.valueOf(now.getDayOfMonth());
        day = String.format(format, Long.parseLong(day));

        String aktuelleZeit = year + "-" + month + "-" + day;

        return aktuelleZeit;
    }
}
