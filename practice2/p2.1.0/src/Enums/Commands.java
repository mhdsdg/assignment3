package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    CreatePlayer("build player (?<name>\\S+) (?<nationality>\\S+) (?<shooting>\\d+) (?<pace>\\d+) (?<dribbling>\\d+) (?<physic>\\d+) (?<passing>\\d+) (?<defending>\\d+)"),
    CreateGoalie("build goalie (?<name>\\S+) (?<nationality>\\S+) (?<diving>\\d+) (?<handling>\\d+) (?<reflex>\\d+) (?<positioning>\\d+) (?<kicking>\\d+) (?<speed>\\d+)"),
    BuyPlayer("buy (?<name>\\S+)"),
    SellPlayer("sell (?<name>\\S+)"),
    SelectPosition("select (?<position>\\S+) (?<name>\\S+)"),
    SetDecoration("set decoration (?<position>\\S+) (?<decoration>\\S+)"),
    SetStrategy("set play style (?<position>\\S+) (?<style>\\S+)"),
    ShowLineUp("show lineup"),
    ShowMoney("show money"),
    CalculateTeamPower("calculate team power"),
    Soot("soot");

    private String pattern ;
    Commands(String pattern) {
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
