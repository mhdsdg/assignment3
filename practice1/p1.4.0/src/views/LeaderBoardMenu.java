package views;

import controllers.LeaderBoardMenuController;
import models.enums.LeadreBoardMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LeaderBoardMenu extends AppMenu{
    LeaderBoardMenuController controller = new LeaderBoardMenuController();
    public final String name = "leaderboard menu";
    public LeaderBoardMenu() {
        super.name = name;
    }
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if(LeadreBoardMenuCommands.ShowRanking.getMatcher(input) != null) {
            controller.showRankings();
        }
        else if(LeadreBoardMenuCommands.ShowHistory.getMatcher(input) != null) {
            controller.showHistory();
        }
        else if(LeadreBoardMenuCommands.Back.getMatcher(input) != null) {
            controller.back();
        }
        else if(LeadreBoardMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            controller.showCurrentMenu();
        }
        else if(LeadreBoardMenuCommands.Exit.getMatcher(input) != null) {
            controller.exit();
        }
        else{
            System.out.println("invalid command");
        }
    }
}
