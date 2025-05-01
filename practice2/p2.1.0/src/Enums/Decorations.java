package Enums;

public enum Decorations {
    Bronze(0 , 1 , 3 , 0 , 0 , 2 , 50000000),
    Silver(2 , 2 , 4 , 2 , 3 , 1 , 70000000),
    Gold(3 , 0 , 3 , 4 , 3 , 1 , 100000000),
    Hero(4 , 2 , 2 , 1 , 1 , 2 , 150000000),
    Icon(5 , 3 , 2 , 0 , 2 , 5 , 200000000);

    String type;
    public final int shootingDiving , paceHandling , dribblingReflex ,
            physicPositioning , passingKicking , defendingSpeed , price ;
    Decorations(int shootingDiving , int paceHandling , int dribblingReflex ,
                int physicPositioning , int passingKicking , int defendingSpeed, int price) {
        this.shootingDiving = shootingDiving;
        this.paceHandling = paceHandling;
        this.dribblingReflex = dribblingReflex;
        this.physicPositioning = physicPositioning;
        this.passingKicking = passingKicking;
        this.defendingSpeed = defendingSpeed;
        this.price = price;
    }
}
