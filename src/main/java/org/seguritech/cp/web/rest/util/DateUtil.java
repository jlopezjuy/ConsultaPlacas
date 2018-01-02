package org.seguritech.cp.web.rest.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class DateUtil {


    public static LocalDateTime getActualDate()
    {
        Calendar c2 = new GregorianCalendar();
        int dia = c2.get(Calendar.DATE);
        int mes = c2.get(Calendar.MONTH);
        int annio = c2.get(Calendar.YEAR);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy hh:mm:ss");

        String date = dia+"/"+(mes+1)+"/"+annio;
        LocalDateTime localDate = LocalDateTime.parse(date, formatter);
        return localDate;
    }
}
