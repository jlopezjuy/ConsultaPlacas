package org.seguritech.cp.web.rest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class DateUtil {


    public static LocalDate getActualDate()
    {
        Calendar c2 = new GregorianCalendar();
        int dia = c2.get(Calendar.DATE);
        int mes = c2.get(Calendar.MONTH);
        int annio = c2.get(Calendar.YEAR);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        String date = dia+"/"+(mes+1)+"/"+annio;
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
}
