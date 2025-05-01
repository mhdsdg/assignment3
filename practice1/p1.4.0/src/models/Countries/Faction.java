package models.Countries;

import java.util.ArrayList;

public class Faction {
    public String name;
    public ArrayList<Countries> countries =  new ArrayList();

    public Faction(String name) {
        this.name = name;
    }
}
