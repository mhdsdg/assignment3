package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our dashboard and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum DashboardCommands  {
    CreateGroup("\\s*create-group\\s+-n\\s+(?<name>.+?)\\s+-t\\s+(?<type>\\S+)\\s*"),
    ShowOwnGroups("\\s*show\\s+my\\s+groups\\s*"),
    AddToGroup("\\s*add-user\\s+-u\\s+(?<username>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<groupid>\\S+)\\s*"),
    AddExpense("\\s*add-expense\\s+-g\\s+(?<groupid>\\S+)\\s+-s\\s+(?<type>(equally|unequally))\\s+-t\\s+(?<totalexpense>\\S+)\\s+-n\\s+(?<numberofusers>\\S+)\\s*"),
    EquallyUser("\\s*(?<username>\\S+)\\s*"),
    UnequallyUser("\\s*(?<username>\\S+)\\s+(?<amount>\\S+)\\s*"),
    ShowBalance("\\s*show\\s+balance\\s+-u\\s+(?<username>\\S+)\\s*"),
    SettleUp("\\s*settle-up\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<inputmoney>\\S+)\\s*"),
    GoToProfile("\\s*go\\s+to\\s+profile\\s+menu\\s*"),
    Logout("\\s*logout\\s*"),
    Exit("\\s*exit\\s*");
    private final String pattern ;
    DashboardCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
