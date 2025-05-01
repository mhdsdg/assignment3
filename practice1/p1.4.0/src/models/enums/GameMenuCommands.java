package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    Countries("German Reich|United States|Soviet Union|Japan|United Kingdom"),
    SwitchPlayer("\\s*switch\\s*player\\s*(?<username>\\S+)\\s*"),
    ShowDetail("\\s*show\\s+detail\\s+(?<countryName>.+?)\\s*"),
    TileOwner("\\s*tile\\s+owner\\s+(?<index>.+?)\\s*"),
    TileNeighbours("\\s*tile\\s+neighbors\\s+(?<index>.+?)\\s*"),
    TileSeaNeighbours("\\s*tile\\s+sea\\s+neighbors\\s+(?<index>.+?)\\s*"),
    TileWeather("\\s*show\\s+weather\\s+(?<index>.+?)\\s*"),
    TileTerrain("\\s*show\\s+terrain\\s+(?<index>.+?)\\s*"),
    TileBattalions("\\s*show\\s+battalions\\s+(?<index>.+?)\\s*"),
    TileFactories("\\s*show\\s+factories\\s+(?<index>.+?)\\s*"),
    TileSetTerrain("\\s*set\\s+terrain\\s+(?<index>.+?)\\s+(?<terrain>\\S+)\\s*"),
    TileSetWeather("\\s*set\\s+weather\\s+(?<index>.+?)\\s+(?<weather>\\S+)\\s*"),
    AddBattalion("\\s*add\\s+battalion\\s+(?<index>.+?)\\s+(?<type>\\S+)\\s+(?<name>\\S+)\\s*"),
    MoveBattalion("\\s*move\\s+battalion\\s+(?<index>.+?)\\s+(?<name>\\S+)\\s+(?<destIndex>.+?)\\s*"),
    UpgradeBattalion("\\s*upgrade\\s+battalion\\s+(?<index>.+?)\\s+(?<name>\\S+)\\s*"),
    CreateFaction("\\s*create\\s+faction\\s+(?<name>\\S+)\\s*"),
    JoinFaction("\\s*join\\s+faction\\s+(?<name>\\S+)\\s*"),
    LeaveFaction("\\s*leave\\s+faction\\s+(?<name>\\S+)\\s*"),
    BuildFactory("\\s*build\\s+factory\\s+(?<index>.+?)\\s+(?<type>.+?)\\s+(?<name>\\S+)\\s*"),
    RunFactory("\\s*run\\s+factory\\s+(?<index>.+?)\\s+(?<name>\\S+)\\s+(?<power>.+?)\\s*"),
    Attack("\\s*attack\\s+(?<index>.+?)\\s+(?<enemyIndex>.+?)\\s+(?<type>\\S+)\\s*"),
    CivilWar("\\s*start\\s+civil\\s+war\\s+(?<tile1>.+?)\\s+(?<tile2>.+?)\\s+(?<type>\\S+)\\s*"),
    StartElection("\\s*start\\s+election\\s*"),
    Puppet("\\s*puppet\\s+(?<name>.+?)\\s*"),
    EndGame("\\s*sadagha\\s+allah\\s+ol\\s+aliol\\s+azim\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    Exit("\\s*exit\\s*");

    private final String pattern;
    GameMenuCommands(String pattern) {
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
