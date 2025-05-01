package Controllers;


import Builders.Builder;
import Decorators.CardDecorator;
import Directors.Director;
import Enums.Decorations;
import Models.App;
import Models.Card;
import Models.Result;
import Strategies.Aggressive;
import Strategies.Balanced;
import Strategies.Defensive;

import java.util.ArrayList;

public class Controller {
    public void buildPlayer(String name , String nationality , String attr1String , String attr2String ,
                            String attr3String , String attr4String , String attr5String , String attr6String) {
        int attr1 = Integer.parseInt(attr1String);
        int attr2 = Integer.parseInt(attr2String);
        int attr3 = Integer.parseInt(attr3String);
        int attr4 = Integer.parseInt(attr4String);
        int attr5 = Integer.parseInt(attr5String);
        int attr6 = Integer.parseInt(attr6String);
        Builder builder = new Builder();
        Director director = new Director();
        director.constructPlayer(builder , name , nationality , attr1 , attr2 , attr3 , attr4 , attr5 , attr6);
    }
    public Result buyPlayer(String name){
        Card card = getCardFromStore(name);
        if(App.getMoney() < card.getPrice()){
            return new Result(false, "8 - 2");
        }
        App.getCards().remove(card);
        App.getBench().add(card);
        App.reduceMoney(card.getPrice());
        return new Result(true, "card bought successfully");
    }
    public void sellPlayer(String name){
        Card card = getCardFromBench(name);
        App.getCards().add(card);
        App.getBench().remove(card);
        App.reduceMoney(-1 * card.getPrice()/2);
    }
    public void selectPosition(String position , String name){
        Card card = getCardFromBench(name);
        App.getBench().remove(card);
        putInLineUp(position , card);
    }
    public void decoratePlayer(String position , String decoration) {
        Card card = null;
        Decorations decorator = null;
        card = getCardFromLineUp(position);
        decorator = switch (decoration) {
            case "bronze" -> Decorations.Bronze;
            case "silver" -> Decorations.Silver;
            case "gold" -> Decorations.Gold;
            case "hero" -> Decorations.Hero;
            case "icon" -> Decorations.Icon;
            default -> null;
        };
        CardDecorator cardDecorator = new CardDecorator(card , decorator);
        putInLineUp(position , cardDecorator);
        assert decorator != null;
        App.reduceMoney(decorator.price);
    }
    public void setPlayStyle(String position , String playStyle){
        Card card = getCardFromLineUp(position);
        switch (playStyle) {
            case "balanced" :
                card.setStrategy(new Balanced());
                break;
            case "defensive" :
                card.setStrategy(new Defensive());
                break;
            case "aggressive" :
                card.setStrategy(new Aggressive());
                break;
        }
        App.reduceMoney(card.getStrategy().cost);
    }
    public void showLineUp(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(App.getSt());
        cards.add(App.getCb());
        cards.add(App.getGk());
        String[] positions = {"striker: " , "center back: " , "goal keeper: "};
        for (int i = 0; i < 3; i++) {
            System.out.print(positions[i]);
            Card card = cards.get(i);
            if(card != null){
                System.out.print(card.getName() + " " + card.getNationality() + " " +
                        card.getAttr1() + " " + card.getAttr2() + " " + card.getAttr3() + " " +
                        card.getAttr4() + " " + card.getAttr5() + " " + card.getAttr6() + " ");
                if(i != 2) System.out.print(card.getStrategy().name);
            }
            System.out.print("\n");
        }
    }
    public void showMoney(){
        System.out.println(App.getMoney());
    }
    public int calculateTeamPower(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(App.getSt());
        cards.add(App.getCb());
        cards.add(App.getGk());
        int totalPower = 0;
        for (int i = 0; i < 3; i++) {
            if(cards.get(i) != null) {
                totalPower += cards.get(i).getStrategy().execute(cards.get(i));
            }
        }
        return totalPower/3;
    }
    public void soot(){
        int totalPower = calculateTeamPower();
        if(totalPower > 90) System.out.println("Visca Barca");
        else if(totalPower < 90) System.out.println("Hala Madrid");
        else System.out.println("draw");
        System.exit(0);
    }


    public Card getCardFromStore(String name){
        for (Card card : App.getCards()) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }
    public Card getCardFromBench(String name){
        for (Card bench : App.getBench()) {
            if (bench.getName().equals(name)) {
                return bench;
            }
        }
        return null;
    }
    public Card getCardFromLineUp(String position){
        switch (position){
            case "st" : return App.getSt();
            case "cb" : return App.getCb();
            case "gk" : return App.getGk();
        }
        return null;
    }
    public void putInLineUp(String position , Card card){
        switch (position){
            case "st" : App.setSt(card);
            break;
            case "cb" : App.setCb(card);
            break;
            case "gk" : App.setGk(card);
            break;
        }
    }
}
