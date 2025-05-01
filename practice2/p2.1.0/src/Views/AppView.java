package Views;

import Models.App;

import java.util.Scanner;

public class AppView {
    public void run(){
        Scanner sc = new Scanner(System.in);
        do{
            App.getMenu().check(sc);
        }while(true);
    }
}
