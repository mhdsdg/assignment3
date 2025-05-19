package View;

import java.util.Scanner;

public class Start {
	public static void main() {
		Scanner scanner = new Scanner(System.in);
		Menu firstMenu = new SignupMenu();
		firstMenu.getCommands(scanner);
	}
}
