package untildawn.practice.Model.Enum.Weapons;

public enum Weapons {
    Revolver(20, 1, 1, 6, "Weapons/RevolverStill.png", "Weapons/RevolverReload"),
    Shotgun(10, 4, 1, 2, "Weapons/ShotgunStill.png", "Weapons/ShotgunReloead"),
    SMG(8, 1, 2, 24, "Weapons/SMGStill.png", "Weapons/SMGReload"),
    ;

    final public int damage, projectile, reloadTime, magSize;
    final public String stillAddress, animationAddress;
    Weapons(int damage, int projectile, int reloadTime, int magSize, String stillAddress, String animationAddress) {
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.magSize = magSize;
        this.animationAddress = animationAddress;
        this.stillAddress = stillAddress;
    }
}
