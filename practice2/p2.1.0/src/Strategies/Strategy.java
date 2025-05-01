package Strategies;

import Models.Card;

public abstract class Strategy {
    public int cost ;
    public String name ;
    public abstract int execute(Card card);
}
