package models.Countries;

import models.User;
import models.enums.BattalionPower;
import models.enums.CaptureRate;

public class UnitedKingdom extends Countries {
    public final String name = "United Kingdom";
    public UnitedKingdom() {
        super.name = name;
        super.manPower = 30000000;
        super.fuel = 0;
        super.sulfur = 1;
        super.steel = 10;
        battalionPower = BattalionPower.UnitedKingdom;
        captureRate = CaptureRate.UnitedKingdom;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
