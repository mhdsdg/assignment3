package untildawn.practice.Model.Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regexes {
    Password("(?=.*[!@#$%^&*()_])(?=.*[A-Za-z])(?=.*\\d).{8,}"),

    ;
    private String regex;
    Regexes(String regex) {
        this.regex = regex;
    }
    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
