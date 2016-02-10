package com.segunfamisa.sample.paystacksample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.paystack.android.model.Card;

/**
 * Created by segun.famisa on 08/02/2016.
 */
public class Utils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");


    /**
     * Formats card with spaces. Portion of code from https://github.com/dbachelder/CreditCardEntry/
     * blob/master/CreditCardEntry/src/com/devmarvel/creditcardentry/fields/CreditCardText.java
     * @param enteredNumber
     * @param maxLength
     * @return
     */
    public static String formatForViewing(String enteredNumber, int maxLength) {
        String cleaned = cleanNumber(enteredNumber);
        int len = cleaned.length();

        if (len <= maxLength)
            return cleaned;

        ArrayList<String> gaps = new ArrayList<>();

        int segmentLengths[] = { 0, 0, 0 };

        // { 4-4-4-4}
        gaps.add(" ");
        segmentLengths[0] = 4;
        gaps.add(" ");
        segmentLengths[1] = 4;
        gaps.add(" ");
        segmentLengths[2] = 4;


        int end = maxLength;
        int start;
        String segment1 = cleaned.substring(0, end);
        start = end;
        end = segmentLengths[0] + end > len ? len : segmentLengths[0] + end;
        String segment2 = cleaned.substring(start, end);
        start = end;
        end = segmentLengths[1] + end > len ? len : segmentLengths[1] + end;
        String segment3 = cleaned.substring(start, end);
        start = end;
        end = segmentLengths[2] + end > len ? len : segmentLengths[2] + end;
        String segment4 = cleaned.substring(start, end);

        String ret = String.format("%s%s%s%s%s%s%s", segment1, gaps.get(0),
                segment2, gaps.get(1), segment3, gaps.get(2), segment4);

        return ret.trim();
    }

    public static String cleanNumber(String number) {
        return number.replaceAll("\\s", "");
    }
}
