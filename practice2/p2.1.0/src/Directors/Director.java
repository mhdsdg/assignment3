package Directors;

import Builders.Builder;
import Models.App;

public class Director {

    public void constructPlayer(Builder builder , String name , String nationality , int attr1 , int attr2 , int attr3
                                , int attr4 , int attr5 , int attr6) {
        builder.setName(name);
        builder.setNationality(nationality);
        builder.setAttr1(attr1);
        builder.setAttr2(attr2);
        builder.setAttr3(attr3);
        builder.setAttr4(attr4);
        builder.setAttr5(attr5);
        builder.setAttr6(attr6);
        App.getCards().add(builder.getResult());
    }
}
