package hongik.enactus.myapplication.common;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getDayNumber(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(day);
    }

    public static String getDayName(Date date){
        String dayName = "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek){
            case 1: dayName = "일"; break;
            case 2: dayName = "월"; break;
            case 3: dayName = "화"; break;
            case 4: dayName = "수"; break;
            case 5: dayName = "목"; break;
            case 6: dayName = "금"; break;
            case 7: dayName = "토"; break;
            default: break;
        }
        return dayName;
    }
}
