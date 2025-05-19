package Controller;

import Model.User;

import java.util.ArrayList;

public class MainMenuController {
	public static final String validPlayMessage = "aghaaz faAliat";

	public static String play(ArrayList<String> names) {
		for (String user : names)
			if (User.getUserByName(user) == null) return "select between available users";

		for (int i = 0; i < names.size(); i++)
			for (int j = i + 1; j < names.size(); j++)
				if (names.get(i).equals(names.get(j))) return "users must be distinct";

		if (names.contains(User.getLoggedInUser().getName())) return "you can't choose urself";
		return validPlayMessage;
	}
}
