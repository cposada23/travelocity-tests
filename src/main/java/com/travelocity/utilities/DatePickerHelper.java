package com.travelocity.utilities;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DatePickerHelper {

    public static DateToSelect getDateToSelect(int monthsFromNow) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateToSelect = localDate.plusMonths(monthsFromNow);
        return getDateFromLocalDate(dateToSelect);
    }

    public static DateToSelect getDateToSelect(int monthsFromNow, int durationInDays) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateToSelect = localDate.plusMonths(monthsFromNow).plusDays(durationInDays);
        return getDateFromLocalDate(dateToSelect);
    }

    private static DateToSelect getDateFromLocalDate(LocalDate date) {
        String month = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
        month = month +  " " + date.getYear();
        return  new DateToSelect(month, String.valueOf(date.getDayOfMonth()));
    }

}
