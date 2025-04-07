package models.Countries;

import models.User;
import models.enums.BattalionPower;
import models.enums.CaptureRate;

public class UnitedStates extends Countries {
    public final String name = "United States";
    public UnitedStates() {
        super.name = name;
        super.manPower = 120000000;
        super.fuel = 200000;
        super.sulfur = 100000;
        super.steel = 200000;
        battalionPower = BattalionPower.UnitedStates;
        captureRate = CaptureRate.UnitedStates;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
