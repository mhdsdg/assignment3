package Decorators;

import Enums.Decorations;
import Models.Card;

public class CardDecorator extends Card{
    private final Decorations decoration;
    public CardDecorator(Card card , Decorations decoration) {
        super(card);
        this.decoration = decoration;
    }

    @Override
    public String getName(){
        return super.getName();
    }

    @Override
    public String getNationality(){
        return super.getNationality();
    }

    @Override
    public int getAttr1(){
        return Math.min(super.getAttr1() + decoration.shootingDiving , 99);
    }

    @Override
    public int getAttr2(){
        return Math.min(super.getAttr2() + decoration.paceHandling , 99);
    }

    @Override
    public int getAttr3(){
        return Math.min(super.getAttr3() + decoration.dribblingReflex , 99);
    }

    @Override
    public int getAttr4(){
        return Math.min(super.getAttr4() + decoration.physicPositioning , 99);
    }

    @Override
    public int getAttr5(){
        return Math.min(super.getAttr5() + decoration.passingKicking , 99);
    }

    @Override
    public int getAttr6(){
        return Math.min(super.getAttr6() + decoration.defendingSpeed , 99);
    }

}
