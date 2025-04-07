package models;

import models.Countries.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private static int idCounter = 1;
    private int id;
    private User currentPlayer = null;
    private ArrayList<User> users;
    private HashMap<User, Countries> players = new HashMap<>();
    private GermanReich germany = new GermanReich();
    private SovietUnion soviet = new SovietUnion();
    private UnitedStates usa = new UnitedStates();
    private UnitedKingdom uk = new UnitedKingdom();
    private Japan japan = new Japan();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Faction> factions = new ArrayList<>();
    public Game(int id , ArrayList<User> users) {
        this.id = id;
        this.users = users;
        mapMaker();
    }

    public Countries getCurrentCountry() {
        return players.get(currentPlayer);
    }
    public void mapMaker() {
        for (int i = 0; i < 16; i++) {
            Tile tile = new Tile(soviet , i+1);
            tiles.add(tile);
            soviet.tiles.add(tile);
        }
        for (int i = 16; i < 34; i++) {
            Tile tile = new Tile(usa , i+1);
            tiles.add(tile);
            usa.tiles.add(tile);
        }
        for (int i = 34; i < 50; i++) {
            Tile tile = new Tile(germany , i+1);
            tiles.add(tile);
            germany.tiles.add(tile);
        }
        for(int i = 50; i < 53; i++) {
            Tile tile = new Tile(japan , i+1);
            tiles.add(tile);
            japan.tiles.add(tile);
        }
        for(int i = 53; i < 56; i++) {
            Tile tile = new Tile(uk , i+1);
            tiles.add(tile);
            uk.tiles.add(tile);
        }
        tiles.get(0).groundNeighbours.add(tiles.get(1));
        tiles.get(0).groundNeighbours.add(tiles.get(4));
        tiles.get(1).groundNeighbours.add(tiles.get(5));
        tiles.get(1).groundNeighbours.add(tiles.get(0));
        tiles.get(1).groundNeighbours.add(tiles.get(2));
        tiles.get(2).groundNeighbours.add(tiles.get(3));
        tiles.get(2).groundNeighbours.add(tiles.get(1));
        tiles.get(2).groundNeighbours.add(tiles.get(6));
        tiles.get(3).groundNeighbours.add(tiles.get(2));
        tiles.get(3).groundNeighbours.add(tiles.get(7));
        tiles.get(4).groundNeighbours.add(tiles.get(0));
        tiles.get(4).groundNeighbours.add(tiles.get(5));
        tiles.get(4).groundNeighbours.add(tiles.get(8));
        tiles.get(5).groundNeighbours.add(tiles.get(4));
        tiles.get(5).groundNeighbours.add(tiles.get(6));
        tiles.get(5).groundNeighbours.add(tiles.get(1));
        tiles.get(5).groundNeighbours.add(tiles.get(9));
        tiles.get(6).groundNeighbours.add(tiles.get(5));
        tiles.get(6).groundNeighbours.add(tiles.get(2));
        tiles.get(6).groundNeighbours.add(tiles.get(7));
        tiles.get(6).groundNeighbours.add(tiles.get(10));
        tiles.get(7).groundNeighbours.add(tiles.get(3));
        tiles.get(7).groundNeighbours.add(tiles.get(6));
        tiles.get(7).groundNeighbours.add(tiles.get(11));
        tiles.get(8).groundNeighbours.add(tiles.get(4));
        tiles.get(8).groundNeighbours.add(tiles.get(9));
        tiles.get(8).groundNeighbours.add(tiles.get(12));
        tiles.get(9).groundNeighbours.add(tiles.get(5));
        tiles.get(9).groundNeighbours.add(tiles.get(8));
        tiles.get(9).groundNeighbours.add(tiles.get(10));
        tiles.get(9).groundNeighbours.add(tiles.get(13));
        tiles.get(10).groundNeighbours.add(tiles.get(6));
        tiles.get(10).groundNeighbours.add(tiles.get(11));
        tiles.get(10).groundNeighbours.add(tiles.get(14));
        tiles.get(10).groundNeighbours.add(tiles.get(9));
        tiles.get(11).groundNeighbours.add(tiles.get(7));
        tiles.get(11).groundNeighbours.add(tiles.get(10));
        tiles.get(11).groundNeighbours.add(tiles.get(15));
        tiles.get(11).groundNeighbours.add(tiles.get(34));
        tiles.get(12).groundNeighbours.add(tiles.get(8));
        tiles.get(12).groundNeighbours.add(tiles.get(13));
        tiles.get(13).groundNeighbours.add(tiles.get(14));
        tiles.get(13).groundNeighbours.add(tiles.get(9));
        tiles.get(13).groundNeighbours.add(tiles.get(12));
        tiles.get(13).groundNeighbours.add(tiles.get(16));
        tiles.get(13).groundNeighbours.add(tiles.get(17));
        tiles.get(14).groundNeighbours.add(tiles.get(15));
        tiles.get(14).groundNeighbours.add(tiles.get(10));
        tiles.get(14).groundNeighbours.add(tiles.get(13));
        tiles.get(14).groundNeighbours.add(tiles.get(18));
        tiles.get(14).groundNeighbours.add(tiles.get(19));
        tiles.get(15).groundNeighbours.add(tiles.get(14));
        tiles.get(15).groundNeighbours.add(tiles.get(11));
        tiles.get(15).groundNeighbours.add(tiles.get(38));
        tiles.get(15).groundNeighbours.add(tiles.get(20));
        tiles.get(15).groundNeighbours.add(tiles.get(21));
        tiles.get(16).groundNeighbours.add(tiles.get(17));
        tiles.get(16).groundNeighbours.add(tiles.get(22));
        tiles.get(16).groundNeighbours.add(tiles.get(13));
        tiles.get(17).groundNeighbours.add(tiles.get(23));
        tiles.get(17).groundNeighbours.add(tiles.get(18));
        tiles.get(17).groundNeighbours.add(tiles.get(16));
        tiles.get(17).groundNeighbours.add(tiles.get(13));
        tiles.get(18).groundNeighbours.add(tiles.get(19));
        tiles.get(18).groundNeighbours.add(tiles.get(24));
        tiles.get(18).groundNeighbours.add(tiles.get(17));
        tiles.get(18).groundNeighbours.add(tiles.get(14));
        tiles.get(19).groundNeighbours.add(tiles.get(20));
        tiles.get(19).groundNeighbours.add(tiles.get(25));
        tiles.get(19).groundNeighbours.add(tiles.get(18));
        tiles.get(19).groundNeighbours.add(tiles.get(14));
        tiles.get(20).groundNeighbours.add(tiles.get(21));
        tiles.get(20).groundNeighbours.add(tiles.get(26));
        tiles.get(20).groundNeighbours.add(tiles.get(19));
        tiles.get(20).groundNeighbours.add(tiles.get(15));
        tiles.get(21).groundNeighbours.add(tiles.get(20));
        tiles.get(21).groundNeighbours.add(tiles.get(27));
        tiles.get(21).groundNeighbours.add(tiles.get(15));
        tiles.get(21).groundNeighbours.add(tiles.get(42));
        tiles.get(22).groundNeighbours.add(tiles.get(23));
        tiles.get(22).groundNeighbours.add(tiles.get(16));
        tiles.get(22).groundNeighbours.add(tiles.get(28));
        tiles.get(23).groundNeighbours.add(tiles.get(17));
        tiles.get(23).groundNeighbours.add(tiles.get(22));
        tiles.get(23).groundNeighbours.add(tiles.get(24));
        tiles.get(23).groundNeighbours.add(tiles.get(29));
        tiles.get(24).groundNeighbours.add(tiles.get(18));
        tiles.get(24).groundNeighbours.add(tiles.get(23));
        tiles.get(24).groundNeighbours.add(tiles.get(25));
        tiles.get(24).groundNeighbours.add(tiles.get(30));
        tiles.get(25).groundNeighbours.add(tiles.get(26));
        tiles.get(25).groundNeighbours.add(tiles.get(24));
        tiles.get(25).groundNeighbours.add(tiles.get(19));
        tiles.get(25).groundNeighbours.add(tiles.get(31));
        tiles.get(26).groundNeighbours.add(tiles.get(27));
        tiles.get(26).groundNeighbours.add(tiles.get(25));
        tiles.get(26).groundNeighbours.add(tiles.get(20));
        tiles.get(26).groundNeighbours.add(tiles.get(32));
        tiles.get(27).groundNeighbours.add(tiles.get(21));
        tiles.get(27).groundNeighbours.add(tiles.get(26));
        tiles.get(27).groundNeighbours.add(tiles.get(33));
        tiles.get(27).groundNeighbours.add(tiles.get(46));
        tiles.get(28).groundNeighbours.add(tiles.get(22));
        tiles.get(28).groundNeighbours.add(tiles.get(29));
        tiles.get(29).groundNeighbours.add(tiles.get(28));
        tiles.get(29).groundNeighbours.add(tiles.get(30));
        tiles.get(29).groundNeighbours.add(tiles.get(23));
        tiles.get(30).groundNeighbours.add(tiles.get(29));
        tiles.get(30).groundNeighbours.add(tiles.get(31));
        tiles.get(30).groundNeighbours.add(tiles.get(24));
        tiles.get(31).groundNeighbours.add(tiles.get(30));
        tiles.get(31).groundNeighbours.add(tiles.get(25));
        tiles.get(31).groundNeighbours.add(tiles.get(32));
        tiles.get(32).groundNeighbours.add(tiles.get(26));
        tiles.get(32).groundNeighbours.add(tiles.get(31));
        tiles.get(32).groundNeighbours.add(tiles.get(33));
        tiles.get(33).groundNeighbours.add(tiles.get(27));
        tiles.get(33).groundNeighbours.add(tiles.get(32));
        tiles.get(34).groundNeighbours.add(tiles.get(11));
        tiles.get(34).groundNeighbours.add(tiles.get(35));
        tiles.get(34).groundNeighbours.add(tiles.get(38));
        tiles.get(35).groundNeighbours.add(tiles.get(34));
        tiles.get(35).groundNeighbours.add(tiles.get(36));
        tiles.get(35).groundNeighbours.add(tiles.get(39));
        tiles.get(36).groundNeighbours.add(tiles.get(35));
        tiles.get(36).groundNeighbours.add(tiles.get(40));
        tiles.get(36).groundNeighbours.add(tiles.get(37));
        tiles.get(36).groundNeighbours.add(tiles.get(53));
        tiles.get(37).groundNeighbours.add(tiles.get(36));
        tiles.get(37).groundNeighbours.add(tiles.get(41));
        tiles.get(37).groundNeighbours.add(tiles.get(54));
        tiles.get(38).groundNeighbours.add(tiles.get(15));
        tiles.get(38).groundNeighbours.add(tiles.get(42));
        tiles.get(38).groundNeighbours.add(tiles.get(39));
        tiles.get(38).groundNeighbours.add(tiles.get(34));
        tiles.get(39).groundNeighbours.add(tiles.get(35));
        tiles.get(39).groundNeighbours.add(tiles.get(43));
        tiles.get(39).groundNeighbours.add(tiles.get(40));
        tiles.get(39).groundNeighbours.add(tiles.get(38));
        tiles.get(40).groundNeighbours.add(tiles.get(41));
        tiles.get(40).groundNeighbours.add(tiles.get(44));
        tiles.get(40).groundNeighbours.add(tiles.get(36));
        tiles.get(40).groundNeighbours.add(tiles.get(39));
        tiles.get(41).groundNeighbours.add(tiles.get(37));
        tiles.get(41).groundNeighbours.add(tiles.get(40));
        tiles.get(41).groundNeighbours.add(tiles.get(45));
        tiles.get(42).groundNeighbours.add(tiles.get(38));
        tiles.get(42).groundNeighbours.add(tiles.get(21));
        tiles.get(42).groundNeighbours.add(tiles.get(43));
        tiles.get(42).groundNeighbours.add(tiles.get(46));
        tiles.get(43).groundNeighbours.add(tiles.get(42));
        tiles.get(43).groundNeighbours.add(tiles.get(39));
        tiles.get(43).groundNeighbours.add(tiles.get(47));
        tiles.get(43).groundNeighbours.add(tiles.get(44));
        tiles.get(44).groundNeighbours.add(tiles.get(40));
        tiles.get(44).groundNeighbours.add(tiles.get(43));
        tiles.get(44).groundNeighbours.add(tiles.get(45));
        tiles.get(44).groundNeighbours.add(tiles.get(48));
        tiles.get(45).groundNeighbours.add(tiles.get(41));
        tiles.get(45).groundNeighbours.add(tiles.get(44));
        tiles.get(45).groundNeighbours.add(tiles.get(49));
        tiles.get(46).groundNeighbours.add(tiles.get(42));
        tiles.get(46).groundNeighbours.add(tiles.get(47));
        tiles.get(46).groundNeighbours.add(tiles.get(27));
        tiles.get(47).groundNeighbours.add(tiles.get(43));
        tiles.get(47).groundNeighbours.add(tiles.get(46));
        tiles.get(47).groundNeighbours.add(tiles.get(48));
        tiles.get(48).groundNeighbours.add(tiles.get(44));
        tiles.get(48).groundNeighbours.add(tiles.get(49));
        tiles.get(48).groundNeighbours.add(tiles.get(47));
        tiles.get(49).groundNeighbours.add(tiles.get(48));
        tiles.get(49).groundNeighbours.add(tiles.get(50));
        tiles.get(49).groundNeighbours.add(tiles.get(45));
        tiles.get(49).groundNeighbours.add(tiles.get(51));
        tiles.get(50).groundNeighbours.add(tiles.get(52));
        tiles.get(50).groundNeighbours.add(tiles.get(49));
        tiles.get(51).groundNeighbours.add(tiles.get(49));
        tiles.get(51).groundNeighbours.add(tiles.get(52));
        tiles.get(52).groundNeighbours.add(tiles.get(51));
        tiles.get(52).groundNeighbours.add(tiles.get(50));
        tiles.get(53).groundNeighbours.add(tiles.get(36));
        tiles.get(53).groundNeighbours.add(tiles.get(54));
        tiles.get(54).groundNeighbours.add(tiles.get(55));
        tiles.get(54).groundNeighbours.add(tiles.get(53));
        tiles.get(54).groundNeighbours.add(tiles.get(37));
        tiles.get(55).groundNeighbours.add(tiles.get(54));

        tiles.get(7).seaNeighbours.add(tiles.get(53));
        tiles.get(7).seaNeighbours.add(tiles.get(34));
        tiles.get(7).seaNeighbours.add(tiles.get(35));
        tiles.get(34).seaNeighbours.add(tiles.get(35));
        tiles.get(34).seaNeighbours.add(tiles.get(7));
        tiles.get(34).seaNeighbours.add(tiles.get(53));
        tiles.get(35).seaNeighbours.add(tiles.get(7));
        tiles.get(35).seaNeighbours.add(tiles.get(34));
        tiles.get(35).seaNeighbours.add(tiles.get(53));
        tiles.get(53).seaNeighbours.add(tiles.get(34));
        tiles.get(53).seaNeighbours.add(tiles.get(35));
        tiles.get(53).seaNeighbours.add(tiles.get(7));

        tiles.get(33).seaNeighbours.add(tiles.get(46));
        tiles.get(46).seaNeighbours.add(tiles.get(33));
        tiles.get(33).seaNeighbours.add(tiles.get(47));
        tiles.get(47).seaNeighbours.add(tiles.get(33));
        tiles.get(33).seaNeighbours.add(tiles.get(48));
        tiles.get(48).seaNeighbours.add(tiles.get(33));
        tiles.get(33).seaNeighbours.add(tiles.get(51));
        tiles.get(51).seaNeighbours.add(tiles.get(33));
        tiles.get(46).seaNeighbours.add(tiles.get(47));
        tiles.get(47).seaNeighbours.add(tiles.get(46));
        tiles.get(46).seaNeighbours.add(tiles.get(48));
        tiles.get(48).seaNeighbours.add(tiles.get(46));
        tiles.get(46).seaNeighbours.add(tiles.get(51));
        tiles.get(51).seaNeighbours.add(tiles.get(46));
        tiles.get(47).seaNeighbours.add(tiles.get(48));
        tiles.get(48).seaNeighbours.add(tiles.get(47));
        tiles.get(47).seaNeighbours.add(tiles.get(51));
        tiles.get(51).seaNeighbours.add(tiles.get(47));
        tiles.get(48).seaNeighbours.add(tiles.get(51));
        tiles.get(51).seaNeighbours.add(tiles.get(48));

        germany.leaders.add(new Leader("hitler" , "fascism"));
        germany.leaders.add(new Leader("pieck" , "communism"));
        germany.leaders.add(new Leader("adenauer" , "democracy"));
        germany.leader = germany.leaders.get(0);
        soviet.leaders.add(new Leader("stalin" , "communism"));
        soviet.leaders.add(new Leader("zombie-lenin" , "democracy"));
        soviet.leaders.add(new Leader("trotsky" , "fascism"));
        soviet.leader = soviet.leaders.get(0);
        usa.leaders.add(new Leader("roosevelt" , "democracy"));
        usa.leaders.add(new Leader("browder" , "communism"));
        usa.leaders.add(new Leader("pelley" , "fascism"));
        usa.leader = usa.leaders.get(0);
        uk.leaders.add(new Leader("churchill" , "democracy"));
        uk.leaders.add(new Leader("mosley" , "fascism"));
        uk.leader = uk.leaders.get(0);
        japan.leaders.add(new Leader("hirohito" , "fascism"));
        japan.leader = japan.leaders.get(0);
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Game.idCounter = idCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }


    public GermanReich getGermany() {
        return germany;
    }

    public void setGermany(GermanReich germany) {
        this.germany = germany;
    }

    public SovietUnion getSoviet() {
        return soviet;
    }

    public void setSoviet(SovietUnion soviet) {
        this.soviet = soviet;
    }

    public UnitedStates getUsa() {
        return usa;
    }

    public void setUsa(UnitedStates usa) {
        this.usa = usa;
    }

    public UnitedKingdom getUk() {
        return uk;
    }

    public void setUk(UnitedKingdom uk) {
        this.uk = uk;
    }

    public Japan getJapan() {
        return japan;
    }

    public void setJapan(Japan japan) {
        this.japan = japan;
    }

    public HashMap<User, Countries> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<User, Countries> players) {
        this.players = players;
    }

    public ArrayList<Faction> getFactions() {
        return factions;
    }

    public void setFactions(ArrayList<Faction> factions) {
        this.factions = factions;
    }
}

