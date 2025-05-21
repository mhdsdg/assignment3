package untildawn.practice.Model.Enum.Weapons;

public enum Weapons {
    Revolver(20, 1, 1, 6, "Weapons/RevolverStill.png", "Weapons/RevolverReload/T_Revolver_SS.png"),
    Shotgun(10, 4, 1, 2, "Weapons/ShotgunStill.png", "Weapons/ShotgunReload/T_Revolver_SS.png"),
    SMG(8, 1, 2, 24, "Weapons/SMGStill.png", "Weapons/SMGReload/T_Revolver_SS.png"),
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
