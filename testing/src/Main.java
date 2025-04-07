import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        int power = 15;
        int terrain = 50;
        int weather = 20;

        double p = power;
        p = p * terrain/100;
        p = p * weather/100;
        System.out.println((int)p);
    }
}