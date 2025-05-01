package Models;

import Decorators.CardDecorator;
import Views.Menu;

import java.util.ArrayList;

public class App {
    private static Menu menu = new Menu();
    private static int money = 1000000000 ;
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Card> bench = new ArrayList<>();
    private static Card gk = null;
    private static Card st = null;
    private static Card cb = null;

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static void setCards(ArrayList<Card> cards) {
        App.cards = cards;
    }

    public static ArrayList<Card> getBench() {
        return bench;
    }

    public static void setBench(ArrayList<Card> bench) {
        App.bench = bench;
    }

    public static Menu getMenu() {
        return menu;
    }

    public static void setMenu(Menu menu) {
        App.menu = menu;
    }

    public static Card getGk() {
        return gk;
    }

    public static void setGk(Card gk) {
        App.gk = gk;
    }

    public static Card getSt() {
        return st;
    }

    public static void setSt(Card st) {
        App.st = st;
    }

    public static Card getCb() {
        return cb;
    }

    public static void setCb(Card cb) {
        App.cb = cb;
    }

    public static int getMoney(){
        return money;
    }

    public static void reduceMoney(int amount){
        money -= amount;
    }
}
