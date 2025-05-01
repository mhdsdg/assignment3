package models.Countries;

import models.User;
import models.enums.BattalionPower;
import models.enums.CaptureRate;

public class SovietUnion extends Countries {
    public final String name = "Soviet Union";

    public SovietUnion(){
        super.name = name;
        super.manPower = 160000000;
        super.fuel = 300000;
        super.sulfur = 50000;
        super.steel = 100000;
        battalionPower = BattalionPower.SovietUnion;
        captureRate = CaptureRate.SovietUnion;
    }
    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
