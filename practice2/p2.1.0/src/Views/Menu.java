package Views;

import Controllers.Controller;
import Enums.Commands;
import Models.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Menu {
    public Controller controller = new Controller();
    public void check(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if((matcher = Commands.CreatePlayer.getMatcher(input)) != null) {
            controller.buildPlayer(matcher.group("name") , matcher.group("nationality") , matcher.group("shooting"),
                    matcher.group("pace") , matcher.group("dribbling") , matcher.group("physic") ,matcher.group("passing"),
                    matcher.group("defending"));
        }
        else if((matcher = Commands.CreateGoalie.getMatcher(input)) != null) {
            controller.buildPlayer(matcher.group("name") , matcher.group("nationality") , matcher.group("diving") ,
                    matcher.group("handling") , matcher.group("reflex") , matcher.group("positioning") ,
                    matcher.group("kicking") , matcher.group("speed"));
        }
        else if((matcher = Commands.BuyPlayer.getMatcher(input)) != null) {
            Result result = controller.buyPlayer(matcher.group("name"));
            if(!result.success()){
                System.out.println(result.message());
            }
        }
        else if((matcher = Commands.SellPlayer.getMatcher(input)) != null) {
            controller.sellPlayer(matcher.group("name"));
        }
        else if((matcher = Commands.SelectPosition.getMatcher(input)) != null) {
            controller.selectPosition(matcher.group("position") , matcher.group("name"));
        }
        else if((matcher = Commands.SetDecoration.getMatcher(input)) != null) {
            controller.decoratePlayer(matcher.group("position") , matcher.group("decoration"));
        }
        else if((matcher = Commands.SetStrategy.getMatcher(input)) != null) {
            controller.setPlayStyle( matcher.group("position") , matcher.group("style"));
        }
        else if((matcher = Commands.ShowLineUp.getMatcher(input)) != null) {
            controller.showLineUp();
        }
        else if((matcher = Commands.ShowMoney.getMatcher(input)) != null) {
            controller.showMoney();
        }
        else if((matcher = Commands.CalculateTeamPower.getMatcher(input)) != null) {
            System.out.println(controller.calculateTeamPower());
        }
        else if((matcher = Commands.Soot.getMatcher(input)) != null) {
            controller.soot();
        }
    }
}
