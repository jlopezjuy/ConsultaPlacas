package org.seguritech.cp.web.rest.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtil {


    public static LocalDateTime getActualDate()
    {
        Date today = new Date();

        //If you print Date, you will get un formatted output
        System.out.println("Today is : " + today);

        //formatting date in Java using SimpleDateFormat
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = DATE_FORMAT.format(today);
        System.out.println(date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }
}
