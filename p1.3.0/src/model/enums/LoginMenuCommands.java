package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    CreateUser("^\\s*create\\s+a\\s+user\\s+account\\s+-fn\\s+(?<firstName>.+?)\\s+-ln\\s+(?<lastName>.+?)\\s+-p\\s+(?<password>.+?)\\s+-rp\\s+(?<reEnteredPassword>.+?)\\s+-e\\s+(?<emailAddress>.+?)\\s*$"),
    CreateStore("^\\s*create\\s+a\\s+store\\s+account\\s+-b\\s+\"(?<brand>.+?)\"\\s+-p\\s+(?<password>.+?)\\s+-rp\\s+(?<reEnteredPassword>.+?)\\s+-e\\s+(?<emailAddress>.+?)\\s*$"),
    LoginUser("^\\s*login\\s+as\\s+user\\s+-e\\s+(?<emailAddress>.+?)\\s+-p\\s+(?<password>.+?)\\s*$"),
    LoginStore("^\\s*login\\s+as\\s+store\\s+-e\\s+(?<emailAddress>.+?)\\s+-p\\s+(?<password>.+?)\\s*$"),
    Logout("\\s*logout\\s*"),
    DeleteAccount("\\s*delete\\s+account\\s+-p\\s+(?<password>.+?)\\s+-rp\\s+(?<reEnterPassword>.+?)\\s*$"),
    GoBack("\\s*go\\s+back\\s*");

    private final String pattern;
    LoginMenuCommands(String pattern) {
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
