package views;

import controllers.GameMenuController;
import models.App;
import models.Result;
import models.enums.GameMenuCommands;
import models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends AppMenu{
    GameMenuController controller = new GameMenuController();
    public final String name = "game menu";
    public GameMenu() {
        super.name = name;
    }
    public void check(Scanner scanner) {
        if(App.getCurrentGame().getCurrentPlayer() == null){
            controller.pickCountry(scanner);
            return;
        }
        String input = scanner.nextLine();
        Matcher matcher;
        if(GameMenuCommands.StartElection.getMatcher(input) != null) {
            controller.StartElection(scanner);
        }
        else if((matcher = GameMenuCommands.SwitchPlayer.getMatcher(input)) != null) {
            System.out.println(controller.switchPlayer(matcher.group("username")).message());
        }
        else if((matcher = GameMenuCommands.ShowDetail.getMatcher(input)) != null) {
            if(checkLock())return;
            Result result = controller.showInformation(matcher.group("countryName"));
            if(!result.isSuccessful()) System.out.println(result.message());
        }
        else if((matcher = GameMenuCommands.TileOwner.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileOwner(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileNeighbours.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileNeighbours(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileSeaNeighbours.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileSeaNeighbours(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileWeather.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileWeather(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileTerrain.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileTerrain(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileBattalions.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileBattalion(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileFactories.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileFactories(matcher.group("index"));
        }
        else if((matcher = GameMenuCommands.TileSetTerrain.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileSetTerrain(matcher.group("index") , matcher.group("terrain"));
        }
        else if((matcher = GameMenuCommands.TileSetWeather.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.tileSetWeather(matcher.group("index") , matcher.group("weather"));
        }
        else if((matcher = GameMenuCommands.AddBattalion.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.addBattalion(matcher.group("index") , matcher.group("type"), matcher.group("name"));
        }
        else if((matcher = GameMenuCommands.MoveBattalion.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.moveBattalion(matcher.group("index") , matcher.group("name"), matcher.group("destIndex"));
        }
        else if((matcher = GameMenuCommands.UpgradeBattalion.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.upgradeBattalion(matcher.group("index") , matcher.group("name"));
        }
        else if((matcher = GameMenuCommands.CreateFaction.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.createFaction(matcher.group("name"));
        }
        else if((matcher = GameMenuCommands.JoinFaction.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.joinFaction(matcher.group("name"));
        }
        else if((matcher = GameMenuCommands.LeaveFaction.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.leaveFaction(matcher.group("name"));
        }
        else if((matcher = GameMenuCommands.BuildFactory.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.buildFactory(matcher.group("index") , matcher.group("type"), matcher.group("name"));
        }
        else if((matcher = GameMenuCommands.RunFactory.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.runFactory(matcher.group("index") , matcher.group("name"), matcher.group("power"));
        }
        else if((matcher = GameMenuCommands.Attack.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.attack(matcher.group("index") , matcher.group("enemyIndex"), matcher.group("type"));
        }
        else if((matcher = GameMenuCommands.CivilWar.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.civilWar(matcher.group("tile1") , matcher.group("tile2"), matcher.group("type"));
        }
        else if((matcher = GameMenuCommands.Puppet.getMatcher(input)) != null) {
            if(checkLock())return;
            controller.puppet(matcher.group("name"));
        }
        else if((GameMenuCommands.EndGame.getMatcher(input)) != null) {
            controller.EndGame();
        }
        else if(GameMenuCommands.Exit.getMatcher(input) != null) {
            App.setCurrentMenu(Menu.EXITMENU);
        }
        else if(GameMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            if(checkLock())return;
            controller.showCurrentMenu();
        }
        else {
            System.out.println("invalid command");
        }
    }
    public boolean checkLock(){
        if(App.getCurrentGame().getCurrentCountry().isLocked) {
            System.out.println("game is locked ");
            return true;
        }
        return false;
    }
}
