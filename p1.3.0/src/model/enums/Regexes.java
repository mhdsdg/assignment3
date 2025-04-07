package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regexes {
    Name("[A-Z][a-z]{2,}"),
    Password("(?=.*[A-Z])(?=.*[a-z])(?=.*[\\d])[A-Za-z\\d]*"),
    Email("(?<mail>[A-Za-z0-9]+(?:\\.[A-Za-z0-9]+)?)@(?<domain>[a-z]+)\\.com"),
    PostalCode("(?<postalCode>[\\d]{10})"),
    CardNumber("(?<cardNumber>[\\d]{16})"),
    CVV("(?<cvv>[\\d]{3,4})"),
    Date("(?<month>\\d{2})/(?<year>\\d{2})"),
    Integer("(?<integer>(-?[1-9]\\d*)|(-?0))"),
    Double("[+-]?\\d+(?:\\.\\d+)?");

    final String pattern;
    private Regexes(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMather(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
