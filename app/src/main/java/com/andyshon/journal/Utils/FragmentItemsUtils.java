package com.andyshon.journal.Utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by andyshon on 16.07.18.
 */

public final class FragmentItemsUtils {
    private FragmentItemsUtils(){}

    public static String getCurrentTimeStamp() {

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        System.out.println("currentDateTimeString:" + currentDateTimeString);

        String[] mas = currentDateTimeString.split("[.]");

        String part1 = mas[0].concat(".,");
        String part2 = mas[2].substring(0,6);

        String date = part1.concat(part2);
        System.out.println("date:" + date);
        return date;
    }

}
