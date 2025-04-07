package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands {
    Register("\\s*register\\s+-username\\s+(?<username>\\S+)\\s+-password\\s+(?<password>.+?)\\s+-email\\s+(?<email>\\S+)\\s*"),
    GoToLogin("\\s*login\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    Exit("\\s*exit\\s*");

    private String pattern;
    SignUpMenuCommands(String pattern) {
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
