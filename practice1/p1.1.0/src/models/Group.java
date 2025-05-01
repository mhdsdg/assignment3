package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import models.enums.GroupType;

import java.util.ArrayList;

public class Group {
    private String name;
    private GroupType type;
    public ArrayList<User> groupMembers = new ArrayList<>();
    private static int idCount = 1;
    private int id;

    public Group(String name, GroupType type , User creator) {
        this.name = name;
        this.type = type;
        groupMembers.add(creator);
        this.id = idCount++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
