package com.segunfamisa.sample.paystacksample;

/**
 * Created by segun.famisa on 11/01/2016.
 */

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 09/01/2016.
 */
public class InputValidator {

    public static String EMAIL_REGEX_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String NUMBER_REGEX_PATTER = "^[0-9]+";

    /**
     * Validates if a text is valid email
     * @param text the text to validate
     * @return true if text is a valid email, false otherwise
     */
    public static boolean isValidEmail(String text){
        Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    /**
     * Validates whether an input is a number
     * @param number number to check
     * @return true if the input is a valid number, false otherwise
     */
    public static boolean isValidNumber(String number){
        Pattern pattern = Pattern.compile(NUMBER_REGEX_PATTER);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    /**
     * To determine if input is short or not
     * @param text input text
     * @param minChar minimum number of characters
     * @return true if the length of text is shorter than minimum characters, and false otherwise.
     */
    public static boolean inputShort(String text, int minChar){
        return TextUtils.isEmpty(text) || text.length() < minChar;
    }
}
