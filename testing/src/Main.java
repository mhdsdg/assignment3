import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.age = 45;
        Person p2 = new Person();
        p2.age = 54;
        int number = 454;
        javaMethod(p1 , p2 , number);
        System.out.println(p1.age + " " + p2.age + " " + number);
    }
    public static void javaMethod(
            Person first,
            Person second,
            int number){

        first.age = 12;
        number = 5;

        Person newP = new Person();
        second = newP;
    }
    public static class Person {
        public int age;
    }
}