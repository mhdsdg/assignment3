package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    GoToSubMenu("\\s*go\\s+to\\s+-m\\s+(?<nameOfTheMenu>(LoginMenu|UserMenu|ProductMenu|StoreMenu))\\s*"),
    Exit("\\s*exit\\s*");

    private String pattern;
    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMather(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
