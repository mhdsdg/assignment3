package models;

/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Expense{
    public Group group;
    public User paidBy;
    public int totalAmount;
    HashMap<User , Integer> shareOfUsers;
    public String splitType;
    public int numberOfShares;
    public Expense(Group group, User paidBy, int totalAmount, HashMap<User , Integer> shareOfUsers,
                   String splitType,  int numberOfShares) {
        this.group = group;
        this.paidBy = paidBy;
        this.totalAmount = totalAmount;
        this.shareOfUsers = shareOfUsers;
        this.splitType = splitType;
        this.numberOfShares = numberOfShares;
    }

    public Result addExpense() {
        Result result ;
        User user = App.getLoggedInUser();
        if (splitType.equals("unequally")){
            int sum = 0;
            for (Map.Entry<User, Integer> userIntegerEntry : shareOfUsers.entrySet()) {
                sum += userIntegerEntry.getValue();
            }
            if (!(sum == totalAmount)) {
                return new Result(false, "the sum of individual costs does not equal the total cost!");
            }
            for (User ower : shareOfUsers.keySet()) {
                user.balance.putIfAbsent(ower , new HashMap<>());
                user.balance.get(ower).putIfAbsent(group , 0);
                ower.balance.putIfAbsent(user , new HashMap<>());
                ower.balance.get(user).putIfAbsent(group , 0);
                int newBalance = user.balance.get(ower).get(group) +(shareOfUsers.get(ower));

                user.balance.get(ower).replace(group , (int)(newBalance));
                ower.balance.get(user).replace(group , (int)(-1*newBalance));
            }
        }
        else{
            for (User ower : shareOfUsers.keySet()) {
                user.balance.putIfAbsent(ower , new HashMap<>());
                user.balance.get(ower).putIfAbsent(group , 0);
                ower.balance.putIfAbsent(user , new HashMap<>());
                ower.balance.get(user).putIfAbsent(group , 0);
                double newBalance = (user.balance.get(ower).get(group) + totalAmount/numberOfShares);
                user.balance.get(ower).replace(group , (int)(newBalance));
                ower.balance.get(user).replace(group , (int)(-1*newBalance));
            }
        }
        result = new Result(true, "expense added successfully!");
        return result;
    }
}
