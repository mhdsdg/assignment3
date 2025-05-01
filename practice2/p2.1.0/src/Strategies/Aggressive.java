package Strategies;

import Models.Card;

public class Aggressive extends Strategy {
    public Aggressive() {
        name = "aggressive";
        cost = 5000000;
    }
    @Override
    public int execute(Card card) {
        return (card.getAttr1() + card.getAttr2() + card.getAttr3()) / 3;
    }
}
