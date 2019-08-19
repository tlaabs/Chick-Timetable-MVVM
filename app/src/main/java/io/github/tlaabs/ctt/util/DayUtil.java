package io.github.tlaabs.ctt.util;

import java.util.Calendar;

import io.github.tlaabs.ctt.model.Days;

public class DayUtil {
    public static int getToday(){
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK) -1;
        if(today < Days.MON.getValue() || today > Days.FRI.getValue()) return -1;
        return today;
    }
}
