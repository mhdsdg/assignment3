package Models;

import Builders.Builder;
import Strategies.Balanced;
import Strategies.Strategy;

public class Card {
    protected final String name;
    protected final String nationality;
    protected final int attr1;
    protected final int attr2;
    protected final int attr3;
    protected final int attr4;
    protected final int attr5;
    protected final int attr6;
    protected Strategy strategy = new Balanced();

    public Card(String name , String nationality, int attr1 ,
                int attr2 , int attr3 , int attr4 , int attr5 , int attr6) {
        this.name = name;
        this.nationality = nationality;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.attr4 = attr4;
        this.attr5 = attr5;
        this.attr6 = attr6;
    }
    public Card(Card card) {
        this.name = card.getName();
        this.nationality = card.getNationality();
        this.attr1 = card.getAttr1();
        this.attr2 = card.getAttr2();
        this.attr3 = card.getAttr3();
        this.attr4 = card.getAttr4();
        this.attr5 = card.getAttr5();
        this.attr6 = card.getAttr6();
    }

    public String getName() {
        return name;
    }
    public String getNationality() {
        return nationality;
    }
    public int getAttr1() {
        return attr1;
    }
    public int getAttr2() {
        return attr2;
    }
    public int getAttr3() {
        return attr3;
    }
    public int getAttr4() {
        return attr4;
    }
    public int getAttr5() {
        return attr5;
    }
    public int getAttr6() {
        return attr6;
    }
    public Strategy getStrategy() {
        return strategy;
    }
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    public int getPrice() {
        return (getAttr1() + getAttr2() + getAttr3() + getAttr4() +
                getAttr5() + getAttr6())/6 * 10000000;
    }
}
