package com.surge.studentmanagement.util;

import java.util.regex.Pattern;

public class DataValidation {
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();

    }
}
