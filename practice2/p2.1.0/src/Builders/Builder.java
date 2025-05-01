package Builders;

import Models.Card;

public class Builder {
    protected String name;
    protected String nationality;
    protected int attr1;
    protected int attr2;
    protected int attr3;
    protected int attr4;
    protected int attr5;
    protected int attr6;

    public void setName(String name){
        this.name = name;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }
    public void setAttr1(int attr1){
        this.attr1 = attr1;
    }
    public void setAttr2(int attr2){
        this.attr2 = attr2;
    }
    public void setAttr3(int attr3){
        this.attr3 = attr3;
    }
    public void setAttr4(int attr4){
        this.attr4 = attr4;
    }
    public void setAttr5(int attr5){
        this.attr5 = attr5;
    }
    public void setAttr6(int attr6){
        this.attr6 = attr6;
    }

    public Card getResult(){
        return new Card(name , nationality, attr1, attr2, attr3, attr4, attr5, attr6);
    }
}
