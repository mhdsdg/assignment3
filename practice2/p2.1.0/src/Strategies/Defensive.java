package Strategies;

import Models.Card;

public class Defensive extends Strategy {
    public Defensive() {
        name = "defensive";
        cost = 4000000;
    }
    @Override
    public int execute(Card card) {
        return (card.getAttr4() + card.getAttr5() + card.getAttr6()) / 3;
    }
}
