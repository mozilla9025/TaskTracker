package task.mozilla9025.com.taskmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    public static String formatDate(long timestamp) {
        Long timestampInMillis = timestamp * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        Date netDate = (new Date(timestampInMillis));
        return sdf.format(netDate);
    }

}
