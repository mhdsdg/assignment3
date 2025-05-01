package models.Countries;

import models.User;
import models.enums.BattalionPower;
import models.enums.CaptureRate;

public class Japan extends Countries {
    final String name = "Japan";
    public Japan() {
        super.name = name;
        super.manPower = 70000000;
        super.fuel = 50000;
        super.sulfur = 50000;
        super.steel = 50000;
        battalionPower = BattalionPower.Japan;
        captureRate = CaptureRate.Japan;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
