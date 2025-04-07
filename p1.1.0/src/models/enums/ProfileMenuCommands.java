package models.enums;
/*
Explanation:
- we have commands in our profile menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    ShowUserInfo("\\s*show\\s+user\\s+info\\s*"),
    ChangeCurrency("\\s*change-currency\\s+-n\\s+(?<newcurrency>\\S+)\\s*"),
    ChangeUsername("\\s*change-username\\s+-n\\s+(?<newusername>\\S+)\\s*"),
    ChangePassword("\\s*change-password\\s+-o\\s+(?<oldpassword>\\S+)\\s+-n\\s+(?<newpassword>\\S+)\\s*"),
    Back("\\s*back\\s*"),
    Exit("\\s*exit\\s*");
    private final String pattern;
    private ProfileMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = java.util.regex.Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
