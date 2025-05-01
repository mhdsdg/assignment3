package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    Login("\\s*login\\s+-username\\s+(?<username>\\S+)\\s+-password\\s+(?<password>.+?)"),
    ForgetPassword("\\s*forget-password\\s+-username\\s+(?<username>\\S+)\\s+-email\\s+(?<email>.+?)\\s*"),
    Back("\\s*back\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    Exit("\\s*exit\\s*");
    private final String pattern;
    LoginMenuCommands(String pattern) {
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
