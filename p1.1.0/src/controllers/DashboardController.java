package controllers;

import models.*;
import models.enums.GroupType;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
public class DashboardController {
    public Result createGroup(String name , String type) {
        User creator = App.getLoggedInUser();
        if (!isNameValid(name)) {
            return new Result(false, "group name format is invalid!");
        }
        if (!isTypeValid(type)) {
            return new Result(false, "group type is invalid!");
        }
        GroupType groupType ;
        if (type.equals("Home")) groupType = GroupType.Home;
        else if(type.equals("Zan-o-Bache")) groupType = GroupType.Zan_o_Bache;
        else if(type.equals("")) groupType = GroupType.Trip;
        else groupType = GroupType.Other;

        Group newGroup = new Group(name, groupType, creator );
        App.Groups.add(newGroup);
        creator.groups.add(newGroup);
        return new Result(true, "group created successfully!");
    }
    public void showOwnGroups() {
        User user = App.getLoggedInUser();
        for (Group group : user.groups) {
            System.out.println("group name : " + group.getName());
            System.out.println("id : " + group.getId());
            System.out.println("type : " + group.getType().getName());
            System.out.println("creator : " + group.groupMembers.get(0).getName());
            System.out.println("members : ");
            for (User member : group.groupMembers) {
                System.out.println(member.getName());
            }
            System.out.println("--------------------");
        }
    }
    public Result addToGroup(String email , String username , String groupIDString) {
        User user = getByUsername(username);
        User adder = App.getLoggedInUser();
        int groupID = Integer.parseInt(groupIDString);
        Group group = getGroupByID(groupID);
        if (user == null) {
            return new Result(false, "user not found!");
        }
        if (group == null) {
            return new Result(false, "group not found!");
        }
        if (isUserInGroup(user , group)) {
            return new Result(false, "user already in the group!");
        }
        if (!user.getEmail().equals(email)) {
            return new Result(false, "the email provided does not match the username!");
        }
        if (!group.groupMembers.get(0).equals(adder)) {
            return new Result(false, "only the group creator can add users!");
        }
        group.groupMembers.add(user);
        user.groups.add(group);
        return new Result(true, "user added to the group successfully!");
    }
    public Result addExpense(String groupIDString , String typeOfSplit , String totalAmountString ,
                             String numberOfUsersString , HashMap<String, Integer> userValue , ArrayList<String> usernames) {
        int groupID = Integer.parseInt(groupIDString);
        Group group = getGroupByID(groupID);
        int numberOfUsers = Integer.parseInt(numberOfUsersString);
        if (group == null) {
            return new Result(false, "group not found!");
        }
        HashMap<User , Integer> users = new HashMap<>() ;
        boolean allUsersInGroup = true;
        ArrayList<String> messages = new ArrayList<>();
        for (String username : usernames) {
            User user = getByUsername(username);
            if (user == null || !isUserInGroup(user , group)) {
                messages.add(username + " not in group!");
                allUsersInGroup = false;
            }
            else users.put(user, (int) (userValue.get(username)*user.getCurrency().value));
        }
        if (!allUsersInGroup) {
            for (int i = 0; i < messages.size() - 1; i++) {
                System.out.println(messages.get(i));
            }
            return new Result(false, messages.get(messages.size() - 1));
        }
        if (!totalAmountString.matches("\\d+")) {
            return new Result(false, "expense format is invalid!");
        }
        int totalAmount = (int)(Integer.parseInt(totalAmountString) * App.getLoggedInUser().getCurrency().value);

        Expense expense = new Expense(group , App.getLoggedInUser(), totalAmount, users, typeOfSplit, numberOfUsers);
        return expense.addExpense();
    }

    public Result showBalance(String username) {
        User user1 = App.getLoggedInUser();
        User user2 = getByUsername(username);
        if (user2 == null) {
            return new Result(false, "user not found!");
        }
        user1.balance.putIfAbsent(user2 , new HashMap<>());
        int totalBalance = 0; //if positive user2 owes user1
        ArrayList<Group> groups = new ArrayList<>();
        for (Group group : user1.groups) {
            if (isUserInGroup(user2 , group)) {
                if(user1.balance.get(user2 ).get(group) != null) {
                    totalBalance += user1.balance.get(user2 ).get(group);
                    groups.add(group);
                }
            }
        }
        Result result ;
        groups.sort(Comparator.comparingInt(Group::getId));
        String groupNames = new String();
        for (Group group2 : groups) {
            groupNames += group2.getName() + ", ";
        }
        groupNames = groupNames.substring(0, groupNames.length() - 2);
        groupNames += "!";
        if(totalBalance < 0) {
            result = new Result(true, "you owe " + username +" "+ -(int)(totalBalance/user1.getCurrency().value)+ " " + user1.getCurrency().symbol + " in " + groupNames);
        }
        else if(totalBalance > 0) {
            result = new Result(true, username + " owes you " + (int)(totalBalance/user1.getCurrency().value) +" " + user1.getCurrency().symbol + " in " + groupNames);
        }
        else {
            result = new Result(true, "you are settled with " + username);
        }
        return result;
    }
    public Result settleUpWith(String username , String amountString){
        User user1 = App.getLoggedInUser();
        ArrayList<Group> groups = user1.groups;
        User user2 = getByUsername(username);
        if (user2 == null) {
            return new Result(false, "user not found!");
        }
        if (!amountString.matches("\\d+")) {
            return new Result(false, "input money format is invalid!");
        }

        groups.sort(Comparator.comparingInt(Group::getId));
        int totalBalance = (int) (Integer.parseInt(amountString) * user1.getCurrency().value);
        Group lastGroup = null;
        for (Group group : groups) {
            if(isUserInGroup(user2 , group)) {
                if (user1.balance.get(user2 ).get(group) != null) {
                    totalBalance += user1.balance.get(user2 ).get(group);
                    user1.balance.get(user2 ).replace(group , 0);
                    user2.balance.get(user1 ).replace(group , 0);
                }
                lastGroup = group;
            }
        }
        user1.balance.get(user2).putIfAbsent(lastGroup , 0);
        user1.balance.get(user2).replace(lastGroup , (totalBalance));
        user2.balance.get(user1).putIfAbsent(lastGroup , 0);
        user2.balance.get(user1).replace(lastGroup , -(totalBalance));
        Result result ;
        if(totalBalance < 0) {
            result = new Result(true, "you owe "+ username +" " + -(int)(totalBalance/user1.getCurrency().value) + " " +user1.getCurrency().symbol + " now!");
        }
        else if(totalBalance > 0) {
            result = new Result(true, username +" owes you " + (int)(totalBalance/user1.getCurrency().value) + " " + user1.getCurrency().symbol + " now!");
        }
        else {
            result = new Result(true, "you are settled with " + username + " now!");
        }
        return result;
    }
    public String goToProfile(){
        App.setCurrentMenu(Menu.PROFILEMENU);
        return "you are now in profile menu!";
    }
    public String logout(){
        App.setCurrentMenu(Menu.LOGINMENU);
        App.setLoggedInUser(null);
        return "user logged out successfully.you are now in login menu!";
    }
    public void exit(){
        App.setCurrentMenu(Menu.EXIT);
    }

    public boolean isNameValid(String name) {
        return name.matches("^[a-zA-Z0-9!@#$%^&* ]{4,30}$");
    }
    public boolean isTypeValid(String type) {
        String[] types = {"Home", "Trip", "Zan-o-Bache", "Other"};
        for (String s : types) {
            if (s.equals(type)) {
                return true;
            }
        }
        return false;
    }
    public Group getGroupByID(int id) {
        if (id > App.Groups.size()) {return null;}
        return App.Groups.get(id - 1);
    }
    public User getByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) { return user; }
        }
        return null;
    }
    public boolean isUserInGroup(User user, Group group) {
        for (Group group1 : user.groups) {
            if (group1.equals(group)) return true;
        }
        return false;
    }
}