package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LeadreBoardMenuCommands {
    ShowRanking("\\s*show\\s+ranking\\s*"),
    ShowHistory("\\s*show\\s+history\\s*"),
    Exit("\\s*exit\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    Back("\\s*back\\s*");
    private String pattern;
    LeadreBoardMenuCommands(String pattern) {
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
