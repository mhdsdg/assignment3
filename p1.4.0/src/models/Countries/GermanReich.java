package models.Countries;

import models.User;
import models.enums.BattalionPower;
import models.enums.CaptureRate;

public class GermanReich extends Countries {
    public final String name = "German Reich" ;
    public GermanReich(){
        super.name = name ;
        super.manPower = 60000000;
        super.fuel = 100000;
        super.sulfur = 200000;
        super.steel = 300000;
        battalionPower = BattalionPower.GermanReich;
        captureRate = CaptureRate.GermanReich;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
