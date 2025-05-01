package models.Countries;

import models.Leader;
import models.Tile;
import models.User;
import models.enums.BattalionPower;
import models.enums.CaptureRate;

import java.util.ArrayList;

public abstract class Countries {
    public String name = "";
    public User player = null;
    public int score = 0;
    public int stability = 100;
    public Leader leader = null;
    public BattalionPower battalionPower = null;
    public CaptureRate captureRate = null;
    public ArrayList<Leader> leaders = new ArrayList<>();
    public ArrayList<Faction> factions = new ArrayList<>();
    public ArrayList<Puppet> puppets = new ArrayList<>();
    public Countries puppeteer = null;
    public ArrayList<Tile> tiles = new ArrayList<>();
    public int manPower = 0, fuel = 0,steel = 0,sulfur = 0 ;
    public boolean isLocked = false;
    public void checkElection(){
        int stabilityLimit = leader.ideology.equals("fascism") ? 30 : leader.ideology.equals("communism") ? 60 : 50;
        if(stability < stabilityLimit) isLocked = true;
    }
}
