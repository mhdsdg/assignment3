package models;

public class Cost {
    public int fuel , steel , sulfur , manPower;
    public Cost(int fuel, int steel, int sulfur, int manPower) {
        this.fuel = fuel;
        this.steel = steel;
        this.sulfur = sulfur;
        this.manPower = manPower;
    }
    public Cost(Cost cost) {
        this.fuel = cost.fuel;
        this.steel = cost.steel;
        this.sulfur = cost.sulfur;
        this.manPower = cost.manPower;
    }
}
