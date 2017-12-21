package com.lithiumsheep.jacketapp.util;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

public class TimeUtil {

    private static final DateTimeFormatter format =
            DateTimeFormatter.ofPattern("MMMM d, h:mm a");

    public static String getTimeForNow() {
        LocalDateTime time = LocalDateTime.now();
        return time.format(format);
    }
}
