package views;
import java.util.*;
/*
Explanation:
This is an  interface class for view of menus.
think about this : menus have some common functions that has different implementation in each menu.
so we can define an interface for menus and then implement it in each menu.
 */

public interface AppMenu {
    public void check(Scanner scanner);
}
