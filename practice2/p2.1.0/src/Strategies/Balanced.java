package Strategies;

import Models.Card;

public class Balanced extends Strategy {
    public Balanced() {
        name = "balanced";
        cost = 3000000;
    }
    @Override
    public int execute(Card card) {
        return (card.getAttr1() + card.getAttr2() + card.getAttr3() + card.getAttr4() +
                card.getAttr5() + card.getAttr6()) / 6;
    }
}
