package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regexes {
    UserName("[a-zA-Z]+([a-zA-Z0-9_]+)?"),
    SpecialChars("%@#$^&!"),
    Password("[a-zA-Z]+([^ ]+)?"),
    Email("(?<mail>[\\.a-zA-Z0-9]+)@(?<domain>.+?)\\.com"),
    Integer("-?[0-9]+");

    private String pattern;
    Regexes(String pattern) {
        this.pattern = pattern;
    }
    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
