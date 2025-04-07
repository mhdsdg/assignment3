package controllers;

import models.*;
import models.Battalion.*;
import models.Countries.Countries;
import models.Countries.Faction;
import models.Countries.Puppet;
import models.Factory.Factory;
import models.Factory.FuelRefinery;
import models.Factory.SteelFactory;
import models.Factory.SulfurFactory;
import models.enums.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuController {
    public void pickCountry(Scanner scanner) {
        ArrayList<String> countries = new ArrayList<>();
        boolean print = false;
        for (int i = 0; i < App.getCurrentGame().getUsers().size(); i++) {
            User user = App.getCurrentGame().getUsers().get(i);
            if(!print) {
                System.out.println("choosing country for " + user.getUsername() + ":");
                print = true;
            }
            String input = scanner.nextLine().trim();
            Matcher matcher = GameMenuCommands.Countries.getMatcher(input);
            if (matcher == null) {
                System.out.println("wrong country name");
                i--;
                continue;
            }
            if (countries.contains(input)){
                System.out.println("country already taken");
                i--;
                continue;
            }
            if(input.equals("German Reich")){
                App.getCurrentGame().getPlayers().put(user , App.getCurrentGame().getGermany());
                App.getCurrentGame().getGermany().setPlayer(user);
            }
            else if(input.equals("Soviet Union")){
                App.getCurrentGame().getPlayers().put(user , App.getCurrentGame().getSoviet());
                App.getCurrentGame().getSoviet().setPlayer(user);
            }
            else if(input.equals("United Kingdom")) {
                App.getCurrentGame().getPlayers().put(user , App.getCurrentGame().getUk());
                App.getCurrentGame().getUk().setPlayer(user);
            }
            else if(input.equals("United States")) {
                App.getCurrentGame().getPlayers().put(user , App.getCurrentGame().getUsa());
                App.getCurrentGame().getUsa().setPlayer(user);
            }
            else {
                App.getCurrentGame().getPlayers().put(user , App.getCurrentGame().getJapan());
                App.getCurrentGame().getJapan().setPlayer(user);
            }
            countries.add(input);
            print = false;
        }
        App.getCurrentGame().setCurrentPlayer(App.getLoggedUser());
    }
    public Result switchPlayer(String username) {
        User user = getUser(username);
        if(user == null){
            return new Result(false, "player doesn't exist");
        }
        if(user == App.getCurrentGame().getCurrentPlayer()){
            return new Result(false, "you can't switch to yourself");
        }
        App.getCurrentGame().setCurrentPlayer(user);
        return new Result(true, "switched to " + username);
    }
    public Result showInformation(String countryString) {
        Matcher matcher = GameMenuCommands.Countries.getMatcher(countryString);
        if(matcher == null) {
            return new Result(false, "country doesn't exist");
        }
        Countries country ;
        if(countryString.equals("German Reich")){
            country = App.getCurrentGame().getGermany();
        }
        else if(countryString.equals("Soviet Union")){
            country = App.getCurrentGame().getSoviet();
        }
        else if(countryString.equals("United Kingdom")){
            country = App.getCurrentGame().getUk();
        }
        else if(countryString.equals("United States")){
            country = App.getCurrentGame().getUsa();
        }
        else {
            country = App.getCurrentGame().getJapan();
        }
        String puppets = "";
        String factions = "";
        for (Puppet puppet : country.puppets) {
            puppets += puppet.country.name + ",";
        }
        for(Faction faction : country.factions){
            factions += faction.name.toLowerCase() + ",";
        }
        if(puppets.length() > 0){
            puppets = puppets.substring(0, puppets.length() - 1);
        }
        if(factions.length() > 0){
            factions = factions.substring(0, factions.length() - 1);
        }
        System.out.println(country.name + "\nleader : " + country.leader.name + "\nstability : " +
                country.stability + "\nman power : " + country.manPower + "\nfuel : " + country.fuel +
                "\nsulfur : " + country.sulfur + "\nsteel : " + country.steel + "\nfaction : " + factions +
                "\npuppet : " + puppets);
        return new Result(true, puppets);
    }
    public void tileOwner(String indexString) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        if(index < 1 || index > 56){
            System.out.println("tile doesn't exist");
            return;
        }
        System.out.println(App.getCurrentGame().getTiles().get(index - 1).getOwner().name);
    }
    public void tileNeighbours(String indexString) {
        if(!tileExists(indexString)){
            return;
        }
        int index = Integer.parseInt(indexString);
        App.getCurrentGame().getTiles().get(index - 1).groundNeighbours.sort(Comparator.comparingInt(n -> n.index));
        String indices = "";
        for(Tile tile : App.getCurrentGame().getTiles().get(index - 1).groundNeighbours){
            indices += tile.index + " , ";
        }
        if(!indices.isEmpty()){
            indices = indices.substring(0, indices.length() - 3);
        }
        System.out.println(indices);
    }
    public void tileSeaNeighbours(String indexString) {
        if(!tileExists(indexString)){
            return;
        }
        int index = Integer.parseInt(indexString);
        App.getCurrentGame().getTiles().get(index - 1).seaNeighbours.sort(Comparator.comparingInt(n -> n.index));
        if(App.getCurrentGame().getTiles().get(index - 1).seaNeighbours.isEmpty()){
            System.out.println("no sea neighbors");
            return;
        }
        String indices = "";
        for (Tile tile : App.getCurrentGame().getTiles().get(index - 1).seaNeighbours) {
            indices += tile.index + " , ";
        }
        if(!indices.isEmpty()){
            indices = indices.substring(0, indices.length() - 3);
        }
        System.out.println(indices);
    }
    public void tileWeather(String indexString) {
        if(!tileExists(indexString)){
            return;
        }
        int index = Integer.parseInt(indexString);
        System.out.println(App.getCurrentGame().getTiles().get(index - 1).weather.toString().toLowerCase());
    }
    public void tileTerrain(String indexString) {
        if(!tileExists(indexString)){
            return;
        }
        int index = Integer.parseInt(indexString);
        System.out.println(App.getCurrentGame().getTiles().get(index - 1).terrain.toString().toLowerCase());
    }
    public void tileBattalion(String indexString) {
        if(!tileExists(indexString)){
            return;
        }
        int index = Integer.parseInt(indexString);
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        if(!hasAccess(game, tile)){
            System.out.println("can't show battalions");
            return;
        }
        tile.getBattalions().sort(Comparator.comparing(Battalion -> Battalion.name.toLowerCase()));
        System.out.println("infantry:");
        for(Battalion bat : tile.getBattalions()){
            if(bat.type.equals("infantry")) System.out.println(bat.name + " " + bat.level + " " + bat.power + " " + bat.captureRatio);
        }
        System.out.println("\npanzer:");
        for(Battalion bat : tile.getBattalions()){
            if(bat.type.equals("panzer")) System.out.println(bat.name + " " + bat.level + " " + bat.power + " " + bat.captureRatio);
        }
        System.out.println("\nairforce:");
        for(Battalion bat : tile.getBattalions()){
            if(bat.type.equals("airforce")) System.out.println(bat.name + " " + bat.level + " " + bat.power + " " + bat.captureRatio);
        }
        System.out.println("\nnavy:");
        for(Battalion bat : tile.getBattalions()){
            if(bat.type.equals("navy")) System.out.println(bat.name + " " + bat.level + " " + bat.power + " " + bat.captureRatio);
        }
    }
    public void tileFactories(String indexString) {
        if(!tileExists(indexString)){
            return;
        }
        int index = Integer.parseInt(indexString);
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        if(!hasAccess(game, tile)){
            System.out.println("can't show factories");
            return;
        }
        tile.getFactories().sort(Comparator.comparing(Factories -> Factories.name.toLowerCase()));
        System.out.println("fuel refinery:");
        for (Factory f : tile.getFactories()) {
            if(f.type.equals("fuel refinery")) System.out.println(f.name + " " + f.getProductionLeft());
        }
        System.out.println("\nsteel factory:");
        for (Factory f : tile.getFactories()) {
            if(f.type.equals("steel factory")) System.out.println(f.name +" " + f.getProductionLeft());
        }
        System.out.println("\nsulfur factory:");
        for (Factory f : tile.getFactories()) {
            if(f.type.equals("sulfur factory")) System.out.println(f.name +" " + f.getProductionLeft());
        }
    }
    public void tileSetTerrain(String indexString , String terrainString) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        if(index <= 0 || index > 56){
            System.out.println("you don't own this tile");
            return;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        if(!tile.getOwner().equals(game.getCurrentCountry())){
            System.out.println("you don't own this tile");
            return;
        }
        Terrain terrain = null;
        if(terrainString.equals(Terrain.Desert.toString().toLowerCase())) terrain = Terrain.Desert;
        else if(terrainString.equals(Terrain.Forest.toString().toLowerCase())) terrain = Terrain.Forest;
        else if(terrainString.equals(Terrain.Plain.toString().toLowerCase())) terrain = Terrain.Plain;
        else if(terrainString.equals(Terrain.Urban.toString().toLowerCase())) terrain = Terrain.Urban;
        else if(terrainString.equals(Terrain.Mountain.toString().toLowerCase())) terrain = Terrain.Mountain;

        if(terrain == null) {
            System.out.println("terrain doesn't exist");
            return;
        }
        if(tile.terrainSet){
            System.out.println("you can't change terrain twice");
            return;
        }
        tile.terrainSet = true;
        tile.terrain = terrain;
        System.out.println("terrain set successfully");
    }
    public void tileSetWeather(String indexString , String weatherString) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        if(index <= 0 || index > 56){
            System.out.println("you don't own this tile");
            return;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        if(!tile.getOwner().equals(game.getCurrentCountry())){
            System.out.println("you don't own this tile");
            return;
        }
        boolean found = true;
        if(weatherString.equals(Weather.Blizzard.toString().toLowerCase())) tile.weather = (Weather.Blizzard);
        else if(weatherString.equals(Weather.Fog.toString().toLowerCase())) tile.weather =(Weather.Fog);
        else if(weatherString.equals(Weather.Rainy.toString().toLowerCase())) tile.weather = (Weather.Rainy);
        else if(weatherString.equals(Weather.SandStorm.toString().toLowerCase())) tile.weather = (Weather.SandStorm);
        else if(weatherString.equals(Weather.Sunny.toString().toLowerCase())) tile.weather =(Weather.Sunny);
        else found = false;
        if(!found) {
            System.out.println("weather doesn't exist");
            return;
        }

        System.out.println("weather set successfully");
    }
    public void addBattalion(String indexString , String batType , String name) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        if(index <= 0 || index > 56){
            System.out.println("tile is unavailable");
            return;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        Countries country = tile.getOwner();
        Countries owner = game.getCurrentCountry();
        if(!hasAccess(game, tile)){
            System.out.println("tile is unavailable");
            return;
        }
        Battalion bat = switch (batType) {
            case "infantry" -> new Infantry(name, country.battalionPower.infantry,
                    country.captureRate.infantry, tile);
            case "airforce" -> new AirForce(name, country.battalionPower.airforce,
                    country.captureRate.airforce, tile);
            case "navy" -> new Navy(name, country.battalionPower.navy,
                    country.captureRate.navy, tile);
            case "panzer" -> new Panzer(name, country.battalionPower.panzer,
                    country.captureRate.panzer, tile);
            default -> null;
        };
        if(bat == null) {
            System.out.println("you can't use imaginary battalions");
            return;
        }
        if(getBattalion(name , tile) != null){
            System.out.println("battalion name already taken");
            return;
        }

        bat.democracyCheck(owner);
        if(!isEnoughForBattalion(owner , bat)){
            System.out.println("daddy USA plz help us");
            return;
        }
        if(!tileHasRoom(batType , tile)) {
            System.out.println("you can't add this type of battalion anymore");
            return;
        }
        tile.getBattalions().add(bat);
        buyBattalion(owner , bat);
        bat.multiplyCost(0.5);
        if(country.leader.ideology.equals("democracy")) bat.multiplyCost(0.5);
        System.out.println("battalion set successfully");
    }
    public void moveBattalion(String indexString , String batName , String destString) {
        if(Regexes.Integer.getMatcher(indexString) == null || Regexes.Integer.getMatcher(destString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        int dest = Integer.parseInt(destString);
        if(index <= 0 || index > 56 || dest <= 0 || dest > 56){
            System.out.println("tile is unavailable");
            return;
        }
        Game game = App.getCurrentGame();
        Countries country = game.getCurrentCountry();
        Tile tile = game.getTiles().get(index - 1);
        Tile destTile = game.getTiles().get(dest - 1);
        if(!hasAccess(game, tile) || !hasAccess(game, destTile)) {
            System.out.println("tile is unavailable");
            return;
        }
        Battalion bat = getBattalion(batName , tile);
        if(bat == null) {
            System.out.println("no battalion with the given name");
            return;
        }
        if(!tileHasRoom(bat.type , destTile)) {
            System.out.println("maximum battalion of this type in destination exists");
            return;
        }
        if(getBattalion(destString , tile) != null) {
            System.out.println("battalion name is already taken in this tile");
            return;
        }
        tile.getBattalions().remove(bat);
        destTile.getBattalions().add(bat);
        bat.tile = destTile;
        System.out.println("battalion moved successfully");
    }
    public void upgradeBattalion(String indexString , String batName) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        if(index <= 0 || index > 56 ){
            System.out.println("can't upgrade battalions on this tile");
            return;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        Countries country = game.getCurrentCountry();
        if(!hasAccess(game, tile)) {
            System.out.println("can't upgrade battalions on this tile");
            return;
        }
        Battalion bat = getBattalion(batName , tile);
        if(bat == null) {
            System.out.println("no battalion with the given name");
            return;
        }
        if(bat.level == 3){
            System.out.println("battalion is on highest level");
            return;
        }
        bat.democracyCheck(country);
        if(!isEnoughForBattalion(country , bat)) {
            System.out.println("aww you can't upgrade your battalion");
            return;
        }
        bat.level++;
        buyBattalion(country , bat);
        bat.multiplyCost(2);
        if(country.leader.ideology.equals("democracy")) bat.multiplyCost(0.5);
        bat.power += bat.level == 1 ? 5 : bat.level == 2 ? 7 : 10 ;
        System.out.println(batName + " upgraded to level " + bat.level);
    }
    public void createFaction(String factionName) {
        Faction faction = getFaction(factionName);
        if(faction != null) {
            System.out.println("faction name already taken");
            return;
        }
        faction = new Faction(factionName);
        App.getCurrentGame().getFactions().add(faction);
        App.getCurrentGame().getCurrentCountry().factions.add(faction);
        faction.countries.add(App.getCurrentGame().getCurrentCountry());
        System.out.println("faction created successfully");
    }
    public void joinFaction(String factionName) {
        Faction faction = getFaction(factionName);
        if(faction == null) {
            System.out.println("faction doesn't exist");
            return;
        }
        faction.countries.add(App.getCurrentGame().getCurrentCountry());
        App.getCurrentGame().getCurrentCountry().factions.add(faction);
        System.out.println(App.getCurrentGame().getCurrentCountry().name + " joined " + factionName);
    }
    public void leaveFaction(String factionName) {
        Faction faction = getFaction(factionName);
        if(faction == null) {
            System.out.println("faction doesn't exist");
            return;
        }
        faction = null;
        for (Faction f : App.getCurrentGame().getCurrentCountry().factions) {
            if(f.name.equals(factionName)) {
                faction = f;
            }
        }
        if(faction == null) {
            System.out.println("your country isn't in this faction");
            return;
        }
        App.getCurrentGame().getCurrentCountry().factions.remove(faction);
        System.out.println( App.getCurrentGame().getCurrentCountry().name + " left " + factionName);
    }
    public void buildFactory(String indexString , String type , String name) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        if(index < 1 || index > 56){
            System.out.println("invalid tile");
            return ;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        if(!hasAccess(game, tile)) {
            System.out.println("invalid tile");
            return;
        }
        Factory factory = null;
        if(type.equals("steel factory")) factory = new SteelFactory(name);
        else if (type.equals("sulfur factory")) factory = new SulfurFactory(name);
        else if (type.equals("fuel refinery")) factory = new FuelRefinery(name);
        if(factory == null) {
            System.out.println("invalid factory type");
            return;
        }
        if(!isEnoughForFactory(game.getCurrentCountry(), factory , tile)) {
            System.out.println("not enough money to build factory");
            return;
        }
        int facCount = 0;
        for (Factory fac : tile.getFactories()) {
            if(fac.type.equals(type)) {
                facCount++;
            }
        }
        if(facCount == 3){
            System.out.println("factory limit exceeded");
            return;
        }
        buyFactory(game.getCurrentCountry(), factory , tile);
        tile.getFactories().add(factory);
        System.out.println("factory built successfully");
    }
    public void runFactory(String indexString , String name , String amountString){
        if(Regexes.Integer.getMatcher(indexString) == null || Regexes.Integer.getMatcher(amountString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        int amount = Integer.parseInt(amountString);
        if(index < 1 || index > 56){
            System.out.println("invalid tile");
            return;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        Countries country = game.getCurrentCountry();
        if(!hasAccess(game, tile)) {
            System.out.println("invalid tile");
            return;
        }
        Factory fac = null;
        for (Factory factory : tile.getFactories()) {
            if(name.equals(factory.name)) {
                fac = factory;
            }
        }
        if(fac == null) {
            System.out.println("this tile doesn't contain this factory");
            return;
        }
        if(amount > country.manPower){
            System.out.println("you are poor!");
            return;
        }
        int produce = amount * fac.productionPerMP;
        if(fac.type.equals("fuel refinery")){
            produce = (int) (produce * (double) tile.terrain.fuelProduction /100);
        }
        if(produce > fac.getProductionLeft()){
            produce = fac.getProductionLeft();
            amount = produce/fac.productionPerMP;
            if(fac.type.equals("fuel refinery")){
                amount /= tile.terrain.fuelProduction;
            }
        }
        fac.runFactory(country , produce , amount);
        if(fac.getProductionLeft() == 0){
            tile.getFactories().remove(fac);
        }
        System.out.println("factory extracted " + produce + " of " + fac.resourceType);
    }
    public void attack(String indexString , String destString , String typeString){
        if(Regexes.Integer.getMatcher(indexString) == null || Regexes.Integer.getMatcher(destString) == null){
            System.out.println("invalid command");
            return;
        }
        int index = Integer.parseInt(indexString);
        int dest = Integer.parseInt(destString);
        if(index < 1 || index > 56){
            System.out.println("attacker tile unavailable");
            return;
        }
        Game game = App.getCurrentGame();
        Tile tile = game.getTiles().get(index - 1);
        Countries attacker = tile.getOwner();
        Countries owner = game.getCurrentCountry();
        if(!canAttack(tile , game)){
            System.out.println("attacker tile unavailable");
            return;
        }
        int batCount = 0;
        for (Battalion bat : tile.getBattalions()) {
            if(bat.type.equals(typeString)) {
                batCount++;
            }
        }
        if(batCount == 0){
            System.out.println("selected tile doesn't have this type of battalion");
            return;
        }
        if(dest < 0 || dest > 56){
            System.out.println("enemy tile unavailable for attacking");
            return;
        }
        Tile destTile = game.getTiles().get(dest - 1);
        Countries enemy = destTile.getOwner();
        if(enemy.equals(attacker)){
            System.out.println("enemy tile unavailable for attacking");
            return;
        }
        if(enemy.equals(attacker.puppeteer)){
            System.out.println("enemy tile unavailable for attacking");
            return;
        }
        for (Faction fac : attacker.factions) {
            for (Countries count : fac.countries) {
                if(enemy.equals(count)) {
                    System.out.println("enemy tile unavailable for attacking");
                    return;
                }
            }
        }
        if(typeString.equals("panzer") || typeString.equals("infantry")){
            if(!tile.groundNeighbours.contains(destTile)){
                System.out.println("enemy tile unavailable for attacking");
                return;
            }
        }
        else if(typeString.equals("navy")){
            if(!tile.seaNeighbours.contains(destTile)){
                System.out.println("enemy tile unavailable for attacking");
                return;
            }
        }
        if(attacker.leader.ideology.equals("fascism") && enemy.leader.ideology.equals("fascism")){
            System.out.println("we are rivals , not enemies");
            return;
        }
        int attackerTP = calculateTP(typeString , tile);
        int enemyTP = calculateTP(typeString , destTile);
        int attackerCap = 0;
        int enemyCap = 0;
        int attackerSize = 0;
        int enemySize = 0;
        int winConst = typeString.equals("panzer") ? 7 : typeString.equals("navy") ? 10 : typeString.equals("infantry") ? 5 : 15;
        for (Battalion bat : tile.getBattalions()) {
            if(!bat.type.equals(typeString))continue;
            attackerSize ++;
            attackerCap = attackerCap == 0 ? bat.captureRatio : attackerCap;
        }
        for (Battalion bat : destTile.getBattalions()) {
            if(!bat.type.equals(typeString))continue;
            enemySize ++;
            enemyCap = enemyCap == 0 ? bat.captureRatio : enemyCap;
        }
        if(attackerTP == enemyTP){
            System.out.println("draw");
            for(Battalion bat : new ArrayList<>(tile.getBattalions())){
                if(!bat.type.equals(typeString))continue;
                tile.getBattalions().remove(bat);
            }
            for(Battalion bat : new ArrayList<>(destTile.getBattalions())){
                if(!bat.type.equals(typeString))continue;
                destTile.getBattalions().remove(bat);
            }
            return;
        }
        else if(attackerTP > enemyTP){
            int rawP = calculateRawPower(typeString , destTile);
            enemyCap *= rawP;
            enemyCap /= 100;
            enemyCap /= attackerSize;
            for(Battalion bat : tile.getBattalions()) {
                if(!bat.type.equals(typeString))continue;
                bat.power += enemyCap;
            }
            for(Battalion bat : new ArrayList<>(destTile.getBattalions())) {
                if(!bat.type.equals(typeString))continue;
                destTile.getBattalions().remove(bat);
            }
            destTile.setOwner(owner);
            attacker.stability *= 1.5;
            if(attacker.stability > 100) attacker.stability = 100;
            enemy.stability *= 0.5;
            enemy.checkElection();
            int powerDiff = -enemyTP + attackerTP;
            owner.score += powerDiff * 10 * winConst;
            enemy.score -= powerDiff * 5 * winConst;
            System.out.println("war is over \n" +
                    "winner : " + attacker.name + "\n" +
                    "loser : " + enemy.name);
        }
        else {
            int rawP = calculateRawPower(typeString , tile);
            attackerCap *= rawP;
            attackerCap /= 100;
            attackerCap /= enemySize;
            for(Battalion bat : new ArrayList<>(tile.getBattalions())){
                if(!bat.type.equals(typeString))continue;
                tile.getBattalions().remove(bat);
            }
            for(Battalion bat : destTile.getBattalions()){
                if(!bat.type.equals(typeString))continue;
                bat.power += attackerCap;
            }
            int powerDiff = enemyTP - attackerTP;
            enemy.steel += powerDiff * 100;
            enemy.sulfur += powerDiff * 100;
            enemy.fuel += powerDiff * 1000;
            enemy.stability *= 1.5;
            if(enemy.stability > 100) enemy.stability = 100;
            attacker.stability *= 0.5;
            owner.score -= powerDiff * 5 * winConst;
            enemy.score += powerDiff * 10 * winConst;
            attacker.checkElection();
            System.out.println("war is over \n" +
                    "winner : " + enemy.name + "\n" +
                    "loser : " + attacker.name);
        }
    }
    public void civilWar(String tile1String , String tile2String , String typeString){
        if(Regexes.Integer.getMatcher(tile1String) == null || Regexes.Integer.getMatcher(tile2String) == null){
            System.out.println("invalid command");
            return;
        }
        Game game = App.getCurrentGame();
        if(game.getCurrentCountry().leader.ideology.equals("democracy")){
            System.out.println("no civil war for you");
            return;
        }
        int tile1Ind = Integer.parseInt(tile1String);
        int tile2Ind = Integer.parseInt(tile2String);
        Tile tile1 = game.getTiles().get(tile1Ind - 1);
        Tile tile2 = game.getTiles().get(tile2Ind - 1);
        if(!tile1.getOwner().equals(game.getCurrentCountry()) || !tile2.getOwner().equals(game.getCurrentCountry())){
            System.out.println("invalid tiles for civil war");
            return;
        }
        int batCounter = 0;
        for (Battalion battalion : tile1.getBattalions()) {
            if(!battalion.type.equals(typeString))continue;
            batCounter++;
        }
        if(batCounter == 0){
            System.out.println("invalid battalion type");
            return;
        }
        if(typeString.equals("panzer") || typeString.equals("infantry")){
            if(!tile1.groundNeighbours.contains(tile2)){
                System.out.println("can't start civil war between these tiles");
                return;
            }
        }
        else if(typeString.equals("navy")){
            if(!tile1.seaNeighbours.contains(tile2)){
                System.out.println("can't start civil war between these tiles");
                return;
            }
        }
        int tile1TP = calculateTP(typeString , tile1);
        int tile2TP = calculateTP(typeString , tile2);
        int tile1Cap = 0;
        int tile2Cap = 0;
        int tile1Size = 0;
        int tile2Size = 0;
        for (Battalion bat : tile1.getBattalions()) {
            if(!bat.type.equals(typeString))continue;
            tile1Cap = tile1Cap == 0 ? bat.captureRatio : tile1Cap;
            tile1Size++;
        }
        for (Battalion bat : tile2.getBattalions()) {
            if(!bat.type.equals(typeString))continue;
            tile2Cap = tile2Cap == 0 ? bat.captureRatio : tile2Cap;
            tile2Size++;
        }
        if(tile1TP == tile2TP){
            System.out.println("man dige harfi nadaram.");
            for(Battalion bat : new ArrayList<Battalion>(tile1.getBattalions())){
                if(!bat.type.equals(typeString))continue;
                tile1.getBattalions().remove(bat);
            }
            for(Battalion bat : new ArrayList<Battalion>(tile2.getBattalions())){
                if(!bat.type.equals(typeString))continue;
                tile2.getBattalions().remove(bat);
            }
            return;
        }
        if(tile1TP < tile2TP){
            int temp = tile1TP;
            tile1TP = tile2TP;
            tile2TP = temp;
            temp = tile1Cap;
            tile1Cap = tile2Cap;
            tile2Cap = temp;
            temp = tile1Size;
            tile1Size = tile2Size;
            tile2Size = temp;
            Tile tempTile = game.getTiles().get(tile1Ind - 1);
            tile1 = tile2;
            tile2 = tempTile;
        }
        int rawP = calculateRawPower(typeString , tile2);
        tile2Cap *= rawP;
        tile2Cap /= 100;
        tile2Cap /= tile1Size;
        for(Battalion bat : tile1.getBattalions()){
            if(!bat.type.equals(typeString))continue;
            bat.power += tile2Cap;
        }
        for(Battalion bat : new ArrayList<>(tile2.getBattalions())){
            if(!bat.type.equals(typeString))continue;
            tile2.getBattalions().remove(bat);
        }
        game.getCurrentCountry().stability *= 0.1;
        game.getCurrentCountry().checkElection();
        System.out.println("civil war ended. " + tile1.index + " won.");
    }
    public void puppet(String countryName){
        Countries country = null;
        Game game = App.getCurrentGame();
        if(countryName.equals("German Reich")) country = game.getGermany();
        else if(countryName.equals("Soviet Union")) country = game.getSoviet();
        else if(countryName.equals("United States")) country = game.getUsa();
        else if(countryName.equals("United Kingdom")) country = game.getUk();
        else if(countryName.equals("Japan")) country = game.getJapan();
        if(country == null){
            System.out.println("country doesn't exist");
            return;
        }
        boolean coFaction = false;
        for (Faction faction : game.getCurrentCountry().factions) {
            for (Countries count : faction.countries) {
                if(count.name.equals(countryName)) coFaction = true;
            }
        }
        if(game.getCurrentCountry().manPower < country.manPower ||
            game.getCurrentCountry().leader.ideology.equals("democracy") ||
            coFaction){
            System.out.println("you are not allowed to puppet this country");
            return;
        }
        country.puppeteer = game.getCurrentCountry();
        game.getCurrentCountry().puppets.add(new Puppet(country));
        System.out.println("now " + countryName + " is my puppet yo ho ha ha ha");
    }
    public void StartElection(Scanner scanner){
        System.out.println("Available leaders:");
        Game game = App.getCurrentGame();
        for (Leader leader : game.getCurrentCountry().leaders) {
            if(leader.ideology.equals("democracy")) System.out.println(leader.name);
        }
        for(Leader leader : game.getCurrentCountry().leaders){
            if(leader.ideology.equals("communism")) System.out.println(leader.name);
        }
        for(Leader leader : game.getCurrentCountry().leaders){
            if(leader.ideology.equals("fascism")) System.out.println(leader.name);
        }
        for (int i = 0; i < 1; i++) {
            String input = scanner.nextLine().trim();
            for (Leader leader : game.getCurrentCountry().leaders) {
                if (leader.name.toLowerCase().equals(input)) {
                    game.getCurrentCountry().leader = leader;
                    game.getCurrentCountry().stability = 100;
                    game.getCurrentCountry().isLocked = false;
                    return;
                }
            }
            System.out.println("leader doesn't exist");
            i--;
        }
    }
    public void EndGame(){
        for (Countries count : App.getCurrentGame().getPlayers().values()) {
            if(count.leader.ideology.equals("fascism")) count.score *= 2;
            count.player.setPoints(count.player.getPoints() + count.score);
        }
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MAINMENU);
    }
    public void showCurrentMenu(){
        System.out.println(App.getCurrentMenu().menu.name);
    }


    public User getUser(String username){
        for (User user : App.getCurrentGame().getUsers()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public boolean hasAccess(Game game , Tile tile) {
        boolean hasAccess = false;
        if(tile.getOwner() == game.getPlayers().get(game.getCurrentPlayer())) hasAccess = true;
        for (Puppet puppet : game.getCurrentCountry().puppets) {
            if(tile.getOwner() == puppet.country)hasAccess = true;
        }
        for (Faction faction : game.getCurrentCountry().factions) {
            for (Countries country : faction.countries) {
                if(tile.getOwner() == country) hasAccess = true;
            }
        }
        return hasAccess;
    }
    public boolean canAttack(Tile tile , Game game) {
        boolean hasAccess = false;
        if(tile.getOwner() == game.getPlayers().get(game.getCurrentPlayer())) hasAccess = true;
        for (Puppet puppet : game.getCurrentCountry().puppets) {
            if(tile.getOwner() == puppet.country)hasAccess = true;
        }
        return hasAccess;
    }
    public boolean tileExists(String indexString) {
        if(Regexes.Integer.getMatcher(indexString) == null){
            System.out.println("invalid command");
            return false;
        }
        int index = Integer.parseInt(indexString);
        if(index < 1 || index > 56){
            System.out.println("tile doesn't exist");
            return false;
        }
        return true;
    }
    public Battalion getBattalion(String name , Tile tile) {
        for (Battalion battalion : tile.getBattalions()) {
            if(battalion.name.equals(name)) {
                return battalion;
            }
        }
        return null;
    }
    public boolean tileHasRoom(String batType , Tile tile) {
        int batCount = 0;
        for (Battalion batt : tile.getBattalions()) {
            if(batt.type.equals(batType)) {
                batCount++;
            }
        }
        if(batCount < 3) return true;
        return false;
    }
    public boolean isEnoughForBattalion(Countries country , Battalion bat) {
        if(country.manPower < bat.cost.manPower ||
                country.fuel < bat.cost.fuel ||
                country.sulfur < bat.cost.sulfur ||
                country.steel < bat.cost.steel) return false;
        return true;
    }
    public boolean isEnoughForFactory(Countries country , Factory factory , Tile tile) {
        int manPowerCost = factory.cost.manPower;
        int steelCost = factory.cost.steel;
        manPowerCost *= tile.terrain.factoryCost;
        manPowerCost /= 100;
        steelCost *= tile.terrain.factoryCost;
        steelCost /= 100;
        if(country.leader.ideology.equals("communism")) {
            manPowerCost /= 2;
            steelCost /= 2;
        }

        if(country.manPower < manPowerCost ||
                country.steel < steelCost) return false;
        return true;
    }
    public void buyBattalion(Countries country , Battalion bat) {
        country.manPower -= bat.cost.manPower;
        country.fuel -= bat.cost.fuel;
        country.sulfur -= bat.cost.sulfur;
        country.steel -= bat.cost.steel;
    }
    public void buyFactory(Countries country , Factory factory , Tile tile) {
        int manPowerCost = factory.cost.manPower;
        int steelCost = factory.cost.steel;
        manPowerCost *= tile.terrain.factoryCost;
        manPowerCost /= 100;
        steelCost *= tile.terrain.factoryCost;
        steelCost /= 100;
        if(country.leader.ideology.equals("communism")) {
            manPowerCost /= 2;
            steelCost /= 2;
        }
        country.manPower -= manPowerCost;
        country.steel -= steelCost;
    }
    public Faction getFaction(String factionName) {
        for (Faction faction : App.getCurrentGame().getFactions()) {
            if (faction.name.equals(factionName)) {
                return faction;
            }
        }
        return null;
    }
    public int calculateTP(String typeString , Tile tile) {
        int TP = 0;
        for (Battalion bat : tile.getBattalions()) {
            if(!bat.type.equals(typeString)) continue;
            TP += bat.power;
        }
        int weatherMod , terrainMod;
        if(typeString.equals("panzer") || typeString.equals("infantry")) {
            weatherMod = tile.weather.attackConst;
            terrainMod = tile.terrain.attack;
        }
        else if(typeString.equals("airforce")) {
            weatherMod = tile.weather.airAttackConst;
            terrainMod = tile.terrain.airAttack;
        }
        else {
            weatherMod = 100;
            terrainMod = 100;
        }
        TP = TP * terrainMod/100;
        TP = TP * weatherMod/100;
        return TP;
    }
    public int calculateRawPower(String typeString , Tile tile) {
        int TP = 0;
        for (Battalion bat : tile.getBattalions()) {
            if(!bat.type.equals(typeString)) continue;
            TP += bat.power;
        }
        return TP;
    }
}
