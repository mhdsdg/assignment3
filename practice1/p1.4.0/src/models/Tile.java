package models;

import java.util.ArrayList;

import models.Battalion.Battalion;
import models.Countries.Countries;
import models.Factory.Factory;
import models.enums.*;

public class Tile {
    public int index;
    private Countries owner;
    public final ArrayList<Tile> groundNeighbours = new ArrayList<Tile>();
    public final ArrayList<Tile> seaNeighbours = new ArrayList<Tile>();
    private ArrayList<Factory> factories = new ArrayList<>();
    private ArrayList<Battalion> battalions = new ArrayList<>();
    public Weather weather = Weather.Sunny;
    public Terrain terrain = Terrain.Plain;
    public boolean terrainSet = false;
    public Tile(Countries owner , int index) {
        this.owner = owner;
        this.index = index;
    }

    public Countries getOwner() {
        return owner;
    }

    public void setOwner(Countries owner) {
        this.owner = owner;
    }

    public ArrayList<Factory> getFactories() {
        return factories;
    }

    public void setFactories(ArrayList<Factory> factories) {
        this.factories = factories;
    }

    public ArrayList<Battalion> getBattalions() {
        return battalions;
    }

    public void setBattalions(ArrayList<Battalion> battalions) {
        this.battalions = battalions;
    }
}
