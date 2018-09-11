package todday.funny.seoulcatcher.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import todday.funny.seoulcatcher.R;

public class DateFormat {
    private final static String DATE_FORMAT = "yyyyy-MM-dd (E)";

    public static String getDdayStringFromCalendar(Context context, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            dateFormat.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateFormat.parse(date));

            Calendar current = Calendar.getInstance();
            GregorianCalendar curreuntCalendar = new GregorianCalendar(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE), 0, 0, 0);

            int dday = (int) ((curreuntCalendar.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / 3600 / 24);

            if (dday == 0) {
                return "D-Day";
            } else if (dday > 0) {
                return dday + context.getString(R.string.days_ago);
            }
            return Math.abs(dday) + context.getString(R.string.days_remaining);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
