package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    Play("\\s*play\\s*(?<user1>\\S+)?(\\s+(?<user2>\\S+))?(\\s+(?<user3>\\S+))?(\\s+(?<user4>\\S+))?\\s*"),
    LeaderBoard("\\s*leaderboard\\s*"),
    Logout("\\s*logout\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    exit("\\s*exit\\s*");
    private String pattern;
    MainMenuCommands(String pattern) {
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
